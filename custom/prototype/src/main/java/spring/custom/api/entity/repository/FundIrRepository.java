package spring.custom.api.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import spring.custom.api.entity.FundIrEntity;
import spring.custom.api.entity.id.FundIrId;

public interface FundIrRepository extends JpaRepository<FundIrEntity, FundIrId>
  , JpaSpecificationExecutor<FundIrEntity> {
  
  List<FundIrEntity> findByFundCdIn(List<String> fundCds);
}
