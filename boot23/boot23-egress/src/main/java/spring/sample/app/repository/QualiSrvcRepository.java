package spring.sample.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.app.entity.QualiSrvcEntity;

public interface QualiSrvcRepository extends JpaRepository<QualiSrvcEntity, String> {

  Optional<QualiSrvcEntity> findById(String id);
  
  Optional<QualiSrvcEntity> findBySrvcName(String srvcName);
  
}
