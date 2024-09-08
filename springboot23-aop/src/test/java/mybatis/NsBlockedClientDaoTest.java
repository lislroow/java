package mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import spring.sample.MainApplication;
import spring.sample.common.aspectj.dao.NsBlockedClientDao;
import spring.sample.common.vo.NsBlockedClientVo;

@MybatisTest
@ContextConfiguration(classes = {MainApplication.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class NsBlockedClientDaoTest {
  
  @Autowired
  private NsBlockedClientDao dao;
  
  @Test
  @Sql("/test-sql/NsBlockedClientDaoTest.sql")
  void whenFindByIpAndTime_thenSuccess() {
    // given
    String remoteAddr = "127.0.0.1";
    Long currentTime = 1725126820848L;

    // when
    List<NsBlockedClientVo> vo = dao.selectBlockedListByIpAndTime(remoteAddr, currentTime);

    // then
    assertThat(vo).isNotNull();
  }
}
