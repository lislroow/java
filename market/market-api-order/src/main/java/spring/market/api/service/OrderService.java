package spring.market.api.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.custom.common.vo.User;
import spring.market.api.dto.OrderReqDto;
import spring.market.api.dto.OrderResDto;
import spring.market.api.entity.Customer;
import spring.market.api.entity.Order;
import spring.market.api.producer.OrderProducer;
import spring.market.api.repository.CustomerRepository;
import spring.market.api.repository.OrderRepository;
import spring.market.common.dto.kafka.OrderDto;
import spring.market.common.vo.UserVo;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
  
  private final OrderRepository repository;
  private final CustomerRepository customerRepository;
  private final OrderProducer producer;
  private final ModelMapper model;
  
  @Transactional
  public void process(OrderReqDto.ItemReq request) {
    // persist
    User user = new User();
    user.setId("01ibc1wnncc6ld");
    Customer customer = customerRepository.findById(user.getId()).orElseThrow();
    Order order = model.map(request, Order.class);
    order.setCustomer(customer);
    
    // 저장
    order = repository.save(order);
    OrderDto orderDto = model.map(order, OrderDto.class);
    
    this.producer.send(orderDto);
    // order 정상 처리 이벤트 등록 (kafka)
    //  - delivery 에서 배송 데이터 생성 
    //  - inventory 에서 재고 데이터 반영
  }
  
  public List<OrderResDto.ItemRes> myOrders(UserVo user) {
    Customer customer = customerRepository.findById(user.getUserId()).orElseThrow();
    List<Order> result = repository.findByCustomerId(customer.getId()).orElseThrow();
    return result.stream()
        .map(item -> model.map(item, OrderResDto.ItemRes.class))
        .toList();
  }
  
}
