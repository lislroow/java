package spring.market.api.service;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.market.api.dto.MyInfoReqDto;
import spring.market.api.dto.MyInfoResDto;
import spring.market.api.entity.Customer;
import spring.market.api.entity.CustomerDelivery;
import spring.market.api.repository.MyDeliveryRepository;
import spring.market.api.repository.MyInfoRepository;
import spring.market.common.vo.UserVo;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MyInfoService {
  
  final MyInfoRepository myInfoRepository;
  final MyDeliveryRepository myDeliveryRepository;
  final ModelMapper model;
  
  public MyInfoResDto.UserRes getUser(UserVo user) {
    Customer entity = myInfoRepository.findByEmail(user.getUserId()).orElseThrow();
    return model.map(entity, MyInfoResDto.UserRes.class);
  }
  
  @Transactional
  public MyInfoResDto.UserRes saveMyInfo(
      UserVo user,
      MyInfoReqDto.UserReq request) {
    Customer entity = myInfoRepository.findById(user.getUserId()).orElseThrow();
    entity.saveBasicInfo(request);
    entity = myInfoRepository.save(entity).orElseThrow();
    return model.map(entity, MyInfoResDto.UserRes.class);
  }
  
  public List<MyInfoResDto.DeliveryAddressRes> getDeliveryAddress(
      UserVo user) {
    String customerId = user.getUserId();
    List<CustomerDelivery> entityList = myDeliveryRepository.findByCustomerId(customerId);
    return entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .toList();
  }
  
  @Transactional
  public List<MyInfoResDto.DeliveryAddressRes> saveDeliveryAddress(
      UserVo user,
      List<MyInfoReqDto.DeliveryAddressReq> request) {
    String customerId = user.getUserId();
    Customer customer = myInfoRepository.findById(customerId).orElseThrow();
    List<CustomerDelivery> entityList = request.stream()
        .map(item -> {
          CustomerDelivery entity = model.map(item, CustomerDelivery.class);
          entity.setCustomer(customer);
          return entity;
        })
        .toList();
    entityList = myDeliveryRepository.saveAll(entityList);
    return entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .toList();
  }
  
  @Transactional
  public List<MyInfoResDto.DeliveryAddressRes> deleteDeliveryAddress(
      UserVo user,
      List<MyInfoReqDto.DeliveryAddressReq> request) {
    List<CustomerDelivery> entityList = request.stream()
        .map(item -> this.myDeliveryRepository.findById(item.getId()).get())
        .toList();
    entityList = myDeliveryRepository.deleteAll(entityList);
    return entityList.stream()
        .map(item -> model.map(item, MyInfoResDto.DeliveryAddressRes.class))
        .toList();
  }
  
}
