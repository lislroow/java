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
      SELECT new spring.custom.api.entity.rec.FundIrCountRec(A.fundCd, COUNT(B.basYmd))
        FROM FundMstEntity A
        LEFT JOIN FundIrEntity B ON A.fundCd = B.fundCd
       WHERE A.deleted = false
       GROUP BY A.fundCd
       ORDER BY A.fundFnm
      """)
  List<FundIrCountRec> findFundIrCount();
  
  List<FundIrEntity> findByFundCdIn(List<String> fundCds);
}
