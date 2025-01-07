package spring.market.api.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.annotation.Login;
import spring.custom.common.annotation.UserInfo;
import spring.market.api.dto.MyInfoReqDto;
import spring.market.api.dto.MyInfoResDto;
import spring.market.api.service.MyInfoService;
import spring.market.common.vo.UserVo;

@RestController
@RequiredArgsConstructor
public class MyInfoController {
  
  private final MyInfoService myInfoService;
  
  @GetMapping("/v1/my/user")
  @Login
  public ResponseEntity<MyInfoResDto.UserRes> getUser(@UserInfo UserVo user) {
    return ResponseEntity.ok(myInfoService.getUser(user));
  }
  
  @PutMapping("/v1/my/user")
  @Login
  public ResponseEntity<MyInfoResDto.UserRes> saveUser(
      @UserInfo UserVo user,
      @RequestBody MyInfoReqDto.UserReq request) {
    return ResponseEntity.ok(myInfoService.saveMyInfo(user, request));
  }

  @GetMapping("/v1/my/delivery-address")
  @Login
  public ResponseEntity<ArrayList<MyInfoResDto.DeliveryAddressRes>> getDeliveryAddress(
      @UserInfo UserVo user) {
    return ResponseEntity.ok(new ArrayList<>(myInfoService.getDeliveryAddress(user)));
  }

  @PutMapping("/v1/my/delivery-address")
  @Login
  public ResponseEntity<ArrayList<MyInfoResDto.DeliveryAddressRes>> saveDeliveryAddress(
      @UserInfo UserVo user,
      @RequestBody List<MyInfoReqDto.DeliveryAddressReq> request) {
    return ResponseEntity.ok(new ArrayList<>(myInfoService.saveDeliveryAddress(user, request)));
  }

  @DeleteMapping("/v1/my/delivery-address")
  @Login
  public ResponseEntity<ArrayList<MyInfoResDto.DeliveryAddressRes>> deleteDeliveryAddress(
      @UserInfo UserVo user,
      @RequestBody List<MyInfoReqDto.DeliveryAddressReq> request) {
    return ResponseEntity.ok(new ArrayList<>(myInfoService.deleteDeliveryAddress(user, request)));
  }
}
