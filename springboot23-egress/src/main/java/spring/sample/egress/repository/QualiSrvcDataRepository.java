package spring.sample.egress.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.egress.entity.QualiSrvcDataEntity;
import spring.sample.egress.entity.QualiSrvcEntity;

public interface QualiSrvcDataRepository extends JpaRepository<QualiSrvcDataEntity, Integer> {
  
  List<QualiSrvcDataEntity> findBySrvc(QualiSrvcEntity srvc);
  
}
