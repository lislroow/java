package spring.sample.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.app.entity.TraceApiEntity;

public interface TraceApiRepository extends JpaRepository<TraceApiEntity, Long> {
  
  Optional<TraceApiEntity> findByRemoteAddr(String remoteAddr);
  
}
