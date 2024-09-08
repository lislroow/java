package spring.sample.common.aspectj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.common.aspectj.entity.NsTraceApiEntity;

public interface NsTraceApiRepository extends JpaRepository<NsTraceApiEntity, Long> {
  
  Optional<NsTraceApiEntity> findByRemoteAddr(String remoteAddr);
  
}
