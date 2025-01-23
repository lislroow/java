package spring.custom.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import spring.custom.api.entity.PlanetEntity;

public interface PlanetRepository extends JpaRepository<PlanetEntity, Integer>
  , JpaSpecificationExecutor<PlanetEntity> {
  
}
