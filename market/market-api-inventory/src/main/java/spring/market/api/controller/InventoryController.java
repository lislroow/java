package spring.market.api.controller;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.dto.ResponseDto;
import spring.market.api.dto.InventoryReqDto;
import spring.market.api.service.InventoryService;

@RestController
@RequiredArgsConstructor
public class InventoryController {
  
  private final InventoryService inventoryService;
  
  @PutMapping("/v1/qty")
  public ResponseDto<Serializable> qty(@RequestBody List<InventoryReqDto.UpdateQty> request) {
    inventoryService.updateQty(request);
    return ResponseDto.body();
  }
  
}
