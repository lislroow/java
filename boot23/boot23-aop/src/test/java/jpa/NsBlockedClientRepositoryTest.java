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
import spring.sample.common.aspectj.entity.NsBlockedClientEntity;
import spring.sample.common.aspectj.repository.NsBlockedClientRepository;

@DataJpaTest
@ContextConfiguration(classes = {Boot23AopMain.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NsBlockedClientRepositoryTest {
  
  @Autowired
  private NsBlockedClientRepository repository;
  
  @BeforeEach
  void setup() {
    long currTime = System.currentTimeMillis();
    NsBlockedClientEntity entity = NsBlockedClientEntity.builder()
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
    Optional<NsBlockedClientEntity> found = repository.findByRemoteAddr(remoteAddr);
    
    assertThat(found.isPresent()).isTrue();
  }
  
}
