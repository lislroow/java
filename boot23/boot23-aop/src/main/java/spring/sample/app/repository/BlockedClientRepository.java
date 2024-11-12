package spring.sample.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.sample.app.entity.BlockedClientEntity;

public interface BlockedClientRepository extends JpaRepository<BlockedClientEntity, Long> {
  
  Optional<BlockedClientEntity> findByRemoteAddr(String remoteAddr);
  
}
