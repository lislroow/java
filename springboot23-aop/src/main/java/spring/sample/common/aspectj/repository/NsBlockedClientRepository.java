package spring.sample.common.aspectj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.common.aspectj.entity.NsBlockedClientEntity;

public interface NsBlockedClientRepository extends JpaRepository<NsBlockedClientEntity, Long> {
  
  Optional<NsBlockedClientEntity> findByRemoteAddr(String remoteAddr);
  
}
