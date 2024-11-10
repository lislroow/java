package mybatis;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import spring.sample.Boot23EgressMain;
import spring.sample.app.dao.QualiSrvcDao;
import spring.sample.app.vo.QualiSrvcDataVo;
import spring.sample.app.vo.QualiSrvcVo;

@MybatisTest
@ContextConfiguration(classes = {Boot23EgressMain.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class QualiSrvcDaoTest {

  @Autowired
  private QualiSrvcDao dao;
  
  @Test
  @Sql("/test-sql/QualiSrvcDaoTest.sql")
  void whenFindById_thenSuccess() {
    // given
    String id = "0001";

    // when
    QualiSrvcVo vo = dao.selectById(id);

    // then
    System.out.println(vo);
    Assertions.assertThat(vo).isNotNull();
  }
  
  @Test
  @Sql("/test-sql/QualiSrvcDaoTest.sql")
  void whenFindDataBySrvcId_thenSuccess() {
    // given
    String id = "0001";
    
    // when
    List<QualiSrvcDataVo> vo = dao.selectDataBySrvcId(id);
    
    // then
    System.out.println(vo);
    Assertions.assertThat(vo.size()).isGreaterThan(0);
  }
}
