package jpa;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import spring.sample.Boot23AopMain;
import spring.sample.app.entity.BlockedClientEntity;
import spring.sample.app.repository.BlockedClientRepository;

@DataJpaTest
@ContextConfiguration(classes = {Boot23AopMain.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NsBlockedClientRepositoryTest {
  
  @Autowired
  private BlockedClientRepository repository;
  
  @BeforeEach
  void setup() {
    long currTime = System.currentTimeMillis();
    BlockedClientEntity entity = BlockedClientEntity.builder()
        .id(UUID.randomUUID().toString())
        .remoteAddr("127.0.0.1")
        .unblockTime(currTime + 100_000L)
        .build();
    repository.save(entity);
  }
  
  @Test
  void whenFindByRemoteAddr_thenSuccess() {
    // given
    String remoteAddr = "127.0.0.1";
    
    // when
    Optional<BlockedClientEntity> found = repository.findByRemoteAddr(remoteAddr);
    
    assertThat(found.isPresent()).isTrue();
  }
  
}
