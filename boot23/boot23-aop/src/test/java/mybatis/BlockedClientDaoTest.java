package mybatis;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;

import spring.sample.Boot23AopMain;
import spring.sample.app.dao.BlockedClientDao;
import spring.sample.app.vo.BlockedClientVo;

@MybatisTest
@ContextConfiguration(classes = {Boot23AopMain.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BlockedClientDaoTest {
  
  @Autowired
  private BlockedClientDao dao;
  
  @Test
  @Sql("/test-sql/BlockedClientDaoTest.sql")
  void whenFindByIpAndTime_thenSuccess() {
    // given
    String remoteAddr = "127.0.0.1";
    Long currentTime = 1725126820848L;

    // when
    List<BlockedClientVo> vo = dao.selectBlockedListByIpAndTime(remoteAddr, currentTime);

    // then
    assertThat(vo).isNotNull();
  }
}
