package spring.market.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.market.api.entity.Customer;

public interface MyInfoRepository extends Repository<Customer, String> {
  
  List<Customer> saveAll(Iterable<Customer> param);
  
  Optional<Customer> findById(String id);
  Optional<Customer> findByEmail(String email);
  
  Optional<Customer> save(Customer param);
  
  Optional<Customer> findByName(String name);
  
}
