package spring.market.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.market.api.entity.Product;

public interface ProductRepository extends Repository<Product, Integer> {
  
  List<Product> saveAll(Iterable<Product> param);
  Optional<Product> findById(Integer param);
  
}
