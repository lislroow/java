package spring.market.api.repository;

import org.springframework.data.repository.Repository;

import spring.market.api.entity.ProductInventory;

public interface InventoryRepository extends Repository<ProductInventory, Integer>{
  
  public ProductInventory findById(Integer param);
  
  public void save(ProductInventory param);
  
}
