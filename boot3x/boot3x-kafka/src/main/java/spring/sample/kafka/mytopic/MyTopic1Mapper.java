package spring.sample.kafka.mytopic;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.sample.config.mybatis.Pageable;
import spring.sample.config.mybatis.PagedList;
import spring.sample.kafka.mytopic.dto.MyTopicVO;

@Mapper
public interface MyTopic1Mapper {
  
  int insert(MyTopicVO data);
  List<MyTopicVO> selectAll();
  PagedList<MyTopicVO> selectList(Pageable param);
  
}
