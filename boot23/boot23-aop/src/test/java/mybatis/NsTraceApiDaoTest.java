package mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import spring.sample.Boot23AopMain;
import spring.sample.common.aspectj.dao.NsTraceApiDao;
import spring.sample.common.vo.NsTraceApiVo;

@MybatisTest
@ContextConfiguration(classes = {Boot23AopMain.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NsTraceApiDaoTest {
  
  @Autowired
  private NsTraceApiDao dao;
  
  @Test
  @Sql("/test-sql/NsTraceApiDaoTest.sql")
  void whenFindById_thenSuccess() {
    // given
    String id = "24c33c06-c0e4-4175-92f0-7f00bd7f0857";

    // when
    NsTraceApiVo vo = dao.selectTraceById(id);

    // then
    assertThat(vo).isNotNull();
  }
}
