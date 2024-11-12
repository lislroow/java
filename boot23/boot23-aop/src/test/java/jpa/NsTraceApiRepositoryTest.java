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
import spring.sample.app.entity.TraceApiEntity;
import spring.sample.app.repository.TraceApiRepository;

@DataJpaTest
@ContextConfiguration(classes = {Boot23AopMain.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NsTraceApiRepositoryTest {
  
  @Autowired
  private TraceApiRepository repository;
  
  @BeforeEach
  void setup() {
    long currTime = System.currentTimeMillis();
    TraceApiEntity entity = TraceApiEntity.builder()
        .id(UUID.randomUUID().toString())
        .remoteAddr("127.0.0.1")
        .param("1234")
        .expireTime(currTime + 60_000L)
        .accessTime(currTime)
        .failYn(false)
        .build();
    repository.save(entity);
  }
  
  @Test
  void whenFindByRemoteAddr_thenSuccess() {
    // given
    String remoteAddr = "127.0.0.1";
    String param = "1234";
    
    // when
    Optional<TraceApiEntity> found = repository.findByRemoteAddr(remoteAddr);
    
    assertThat(found.isPresent()).isTrue();
    assertThat(found.get().getParam()).isEqualTo(param);
  }
  
}
