package spring.scheduler.api.entity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import spring.scheduler.api.entity.SysErrorLogEntity;

public interface SysErrorLogRepository extends JpaRepository<SysErrorLogEntity, Long>
, JpaSpecificationExecutor<SysErrorLogEntity> {
  
}
