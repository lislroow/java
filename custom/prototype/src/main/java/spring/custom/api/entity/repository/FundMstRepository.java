package spring.custom.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import spring.custom.api.entity.FundMstEntity;

public interface FundMstRepository extends JpaRepository<FundMstEntity, String>
  , JpaSpecificationExecutor<FundMstEntity> {
  
}
