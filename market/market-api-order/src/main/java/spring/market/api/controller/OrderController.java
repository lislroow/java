package spring.market.api.controller;

import java.io.Serializable;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.market.api.dto.OrderReqDto;
import spring.market.api.dto.OrderResDto;
import spring.market.api.service.OrderService;
import spring.market.common.aop.annotation.Login;
import spring.market.common.aop.annotation.UserInfo;
import spring.component.common.dto.ResponseDto;
import spring.market.common.vo.UserVo;

@RestController
@RequiredArgsConstructor
public class OrderController {
  
  private final OrderService orderService;
  
  @PostMapping("/v1/process")
  public ResponseDto<Serializable> orderProducts(@RequestBody OrderReqDto.ItemReq request) {
    orderService.process(request);
    return ResponseDto.body();
  }
  
  @GetMapping("/v1/my/orders")
  @Login
  public ResponseDto<OrderResDto.ItemListRes> myOrder(@UserInfo UserVo user) {
    OrderResDto.ItemListRes resDto = new OrderResDto.ItemListRes();
    resDto.setList(orderService.myOrders(user));
    return ResponseDto.body(resDto);
  }
}
