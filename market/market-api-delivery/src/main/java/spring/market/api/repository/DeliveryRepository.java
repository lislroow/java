package spring.market.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import spring.market.api.entity.Delivery;
import spring.market.api.entity.id.DeliveryId;

public interface DeliveryRepository extends Repository<Delivery, DeliveryId> {
  
  Optional<List<Delivery>> findByOrderId(Integer param);
  
  List<Delivery> saveAll(Iterable<Delivery> param);
  
}
