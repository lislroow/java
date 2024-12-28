package spring.market.api.controller;

import java.util.ArrayList;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.component.common.dto.ResponseDto;
import spring.market.api.dto.DeliveryResDto;
import spring.market.api.service.DeliveryService;

@RestController
@RequiredArgsConstructor
public class DeliveryController {
  
  private final DeliveryService deliveryService;
  
  @GetMapping("/v1/status/{orderId}")
  public ResponseDto<ArrayList<DeliveryResDto.StatusRes>> getOrder(
      @PathVariable Integer orderId) {
    return ResponseDto.body(new ArrayList<>(deliveryService.getOrder(orderId)));
  }
}
