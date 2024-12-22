package spring.sample.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.app.entity.AccessControlEntity;

public interface AccessControlRepository extends JpaRepository<AccessControlEntity, Long> {
  
  Optional<AccessControlEntity> findByIpAddr(String ipAddr);
  
}
