package jpa;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import spring.sample.Boot23EgressMain;
import spring.sample.app.entity.QualiSrvcEntity;
import spring.sample.app.repository.QualiSrvcRepository;

@DataJpaTest
@ContextConfiguration(classes = {Boot23EgressMain.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QualiSrvcRepositoryTest {
  
  @Autowired
  private QualiSrvcRepository repository;
  
  @BeforeEach
  void setup() {
    QualiSrvcEntity entity = QualiSrvcEntity.builder()
        .id("0001")
        .srvcName("국가유공자 자격여부")
        .build();
    repository.save(entity);
  }
  
  @Test
  void whenFindByRemoteAddr_thenSuccess() {
    // given
    String srvcName = "국가유공자 자격여부";
    
    // when
    Optional<QualiSrvcEntity> found = repository.findBySrvcName(srvcName);
    
    Assertions.assertThat(found.isPresent()).isTrue();
  }
  
}
