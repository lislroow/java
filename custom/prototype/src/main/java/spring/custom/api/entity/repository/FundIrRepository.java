package spring.custom.api.entity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import spring.custom.api.entity.FundIrEntity;
import spring.custom.api.entity.id.FundIrId;
import spring.custom.api.entity.rec.FundIrCountRec;

public interface FundIrRepository extends JpaRepository<FundIrEntity, FundIrId>
  , JpaSpecificationExecutor<FundIrEntity> {
  
  @Query("""
      SELECT new spring.custom.api.entity.rec.FundIrCountRec(A.fundCd, COUNT(A.basYmd))
        FROM FundIrEntity A
       GROUP BY A.fundCd
      """)
  List<FundIrCountRec> findFundIrCount();
  
  List<FundIrEntity> findByFundCdIn(List<String> fundCds);
}
