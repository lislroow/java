package spring.market.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.dto.ResponseDto;
import spring.market.api.dto.MyInfoReqDto;
import spring.market.api.dto.MyInfoResDto;
import spring.market.api.service.MyInfoService;
import spring.market.common.aop.annotation.Login;
import spring.market.common.aop.annotation.UserInfo;
import spring.market.common.vo.UserVo;

@RestController
@RequiredArgsConstructor
public class MyInfoController {
  
  private final MyInfoService myInfoService;
  
  @GetMapping("/v1/my/user")
  @Login
  public ResponseDto<MyInfoResDto.UserRes> getUser(@UserInfo UserVo user) {
    return ResponseDto.body(myInfoService.getUser(user));
  }
  
  @PutMapping("/v1/my/user")
  @Login
  public ResponseDto<MyInfoResDto.UserRes> saveUser(
      @UserInfo UserVo user,
      @RequestBody MyInfoReqDto.UserReq request) {
    return ResponseDto.body(myInfoService.saveMyInfo(user, request));
  }

  @GetMapping("/v1/my/delivery-address")
  @Login
  public ResponseDto<ArrayList<MyInfoResDto.DeliveryAddressRes>> getDeliveryAddress(
      @UserInfo UserVo user) {
    return ResponseDto.body(new ArrayList<>(myInfoService.getDeliveryAddress(user)));
  }

  @PutMapping("/v1/my/delivery-address")
  @Login
  public ResponseDto<ArrayList<MyInfoResDto.DeliveryAddressRes>> saveDeliveryAddress(
      @UserInfo UserVo user,
      @RequestBody List<MyInfoReqDto.DeliveryAddressReq> request) {
    return ResponseDto.body(new ArrayList<>(myInfoService.saveDeliveryAddress(user, request)));
  }

  @DeleteMapping("/v1/my/delivery-address")
  @Login
  public ResponseDto<ArrayList<MyInfoResDto.DeliveryAddressRes>> deleteDeliveryAddress(
      @UserInfo UserVo user,
      @RequestBody List<MyInfoReqDto.DeliveryAddressReq> request) {
    return ResponseDto.body(new ArrayList<>(myInfoService.deleteDeliveryAddress(user, request)));
  }
}
