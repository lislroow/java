package spring.market.api.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.market.api.dto.InventoryReqDto;
import spring.market.api.entity.ProductInventory;
import spring.market.api.repository.InventoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {
  
  private final InventoryRepository inventoryRepository;
  
  @Transactional
  public void updateQty(List<InventoryReqDto.UpdateQty> request) {
    request.stream().forEach(item -> {
      ProductInventory entity = inventoryRepository.findById(item.getId());
      entity.updateQty(item);
      inventoryRepository.save(entity);
    });
  }
  
}
