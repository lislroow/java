package spring.market.api.repository;

import org.springframework.data.repository.Repository;

import spring.market.api.entity.Order;

public interface OrderRepository extends Repository<Order, Integer>{
  
  void save(Order param);
  
}
