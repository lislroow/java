package jpa;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import spring.sample.MainApplication;
import spring.sample.code.CD_QV_DATA_TYPE;
import spring.sample.code.CD_REQ_RES;
import spring.sample.egress.entity.QualiSrvcDataEntity;
import spring.sample.egress.entity.QualiSrvcEntity;
import spring.sample.egress.repository.QualiSrvcDataRepository;
import spring.sample.egress.repository.QualiSrvcRepository;

@DataJpaTest
@ContextConfiguration(classes = {MainApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QualiSrvcDataRepositoryTest {

  @Autowired
  private QualiSrvcRepository repository;
  
  @Autowired
  private QualiSrvcDataRepository qualiSrvcDataRepository;
  
  //@BeforeEach
  @Test
  void setup() {
    QualiSrvcEntity entity = QualiSrvcEntity.builder()
        .id("0001")
        .srvcName("국가유공자 자격여부")
        .build();
    entity.setDatas(Arrays.asList(
        QualiSrvcDataEntity.builder()
          .dataNameKo("주민등록번호")
          .dataNameEn("juminNo")
          .reqRes(CD_REQ_RES.REQ)
          .dataType(CD_QV_DATA_TYPE.NUM)
          .srvc(entity)
          .build()
        , QualiSrvcDataEntity.builder()
          .dataNameKo("이름")
          .dataNameEn("resName")
          .reqRes(CD_REQ_RES.RES)
          .dataType(CD_QV_DATA_TYPE.HANGLE)
          .srvc(entity)
          .build()
        , QualiSrvcDataEntity.builder()
          .dataNameKo("주민등록번호")
          .dataNameEn("resSecrNum")
          .reqRes(CD_REQ_RES.RES)
          .dataType(CD_QV_DATA_TYPE.NUM)
          .srvc(entity)
          .build()
        ));
    repository.save(entity);
  }
  
//  @Test
  void whenFindBySrvcId_thenSuccess() {
    // given
    String id = "0001";
    
    // when
    Optional<QualiSrvcEntity> entity = repository.findById(id);
    Assertions.assertThat(entity.isPresent()).isTrue();
    System.out.println(entity.get());
    
    List<QualiSrvcDataEntity> datas = entity.get().getDatas();
    Assertions.assertThat(datas.size()).isGreaterThan(0);
    System.out.println(datas);
  }
  
}
