package spring.custom.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import spring.custom.api.entity.SatelliteEntity;

public interface SatelliteRepository extends JpaRepository<SatelliteEntity, Integer>
  , JpaSpecificationExecutor<SatelliteEntity> {
  
}
