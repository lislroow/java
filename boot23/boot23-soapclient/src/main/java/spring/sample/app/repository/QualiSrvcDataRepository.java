package spring.sample.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.app.entity.QualiSrvcDataEntity;
import spring.sample.app.entity.QualiSrvcEntity;

public interface QualiSrvcDataRepository extends JpaRepository<QualiSrvcDataEntity, Integer> {
  
  List<QualiSrvcDataEntity> findBySrvc(QualiSrvcEntity srvc);
  
}
