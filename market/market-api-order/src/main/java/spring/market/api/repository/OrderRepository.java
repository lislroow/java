package spring.market.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.market.api.entity.Order;

public interface OrderRepository extends Repository<Order, Integer>{
  
  Optional<Order> findById(Order param);
  
  Optional<List<Order>> findByCustomerId(String customerId);
  
  List<Order> saveAll(Iterable<Order> param);
  
  Order save(Order param);
}
