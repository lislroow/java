package spring.sample.kafka.mytopic;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.sample.config.mybatis.Pageable;
import spring.sample.config.mybatis.PagedList;
import spring.sample.kafka.mytopic.dto.MyTopicVO;

@Service
@Transactional(readOnly = true)
public class MyTopic1Service {
  
  private MyTopic1Mapper mapper;
  
  public MyTopic1Service(MyTopic1Mapper mapper) {
    this.mapper = mapper;
  }
  
  public List<MyTopicVO> selectAll() {
    List<MyTopicVO> list = null;
    list = mapper.selectAll();
    return list;
  }
  
  public PagedList<MyTopicVO> selectList(Pageable param) {
    return mapper.selectList(param);
  }
}
