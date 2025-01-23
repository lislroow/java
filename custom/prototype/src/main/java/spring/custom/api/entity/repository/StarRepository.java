package spring.custom.api.entity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import spring.custom.api.entity.StarEntity;

public interface StarRepository extends JpaRepository<StarEntity, Integer>, JpaSpecificationExecutor<StarEntity> {
  
  Optional<StarEntity> findById(Integer id);

  List<StarEntity> findByDistanceGreaterThanEqual(Double distance);
  
}
