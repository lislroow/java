package spring.sample.egress.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.egress.entity.QualiSrvcEntity;

public interface QualiSrvcRepository extends JpaRepository<QualiSrvcEntity, String> {

  Optional<QualiSrvcEntity> findById(String id);
  
  Optional<QualiSrvcEntity> findBySrvcName(String srvcName);
  
}
