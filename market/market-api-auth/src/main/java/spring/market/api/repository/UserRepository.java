package spring.market.api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import spring.market.api.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

  UserEntity findByOauth2Id(String param);
  
  Optional<UserEntity> findByEmail(String param);
  
  UserEntity save(UserEntity param);
  
}
