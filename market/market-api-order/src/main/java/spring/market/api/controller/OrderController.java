package spring.market.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.annotation.Login;
import spring.custom.common.annotation.UserInfo;
import spring.market.api.dto.OrderReqDto;
import spring.market.api.dto.OrderResDto;
import spring.market.api.service.OrderService;
import spring.market.common.vo.UserVo;

@RestController
@RequiredArgsConstructor
public class OrderController {
  
  private final OrderService orderService;
  
  @PostMapping("/v1/process")
  public ResponseEntity<?> orderProducts(@RequestBody OrderReqDto.ItemReq request) {
    orderService.process(request);
    return ResponseEntity.ok().build();
  }
  
  @GetMapping("/v1/my/orders")
  @Login
  public ResponseEntity<OrderResDto.ItemListRes> myOrder(@UserInfo UserVo user) {
    OrderResDto.ItemListRes resDto = new OrderResDto.ItemListRes();
    resDto.setList(orderService.myOrders(user));
    return ResponseEntity.ok(resDto);
  }
}
