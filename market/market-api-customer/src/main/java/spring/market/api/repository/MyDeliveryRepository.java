package spring.market.api.repository;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.market.api.entity.CustomerDelivery;

public interface MyDeliveryRepository extends Repository<CustomerDelivery, Integer> {

  Optional<CustomerDelivery> findById(Integer param);
  
  ArrayList<CustomerDelivery> findByCustomerId(String param);
  
  ArrayList<CustomerDelivery> saveAll(Iterable<CustomerDelivery> param);
  
  ArrayList<CustomerDelivery> deleteAll(Iterable<CustomerDelivery> param);
  
}
