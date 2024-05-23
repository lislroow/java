package spring.sample.kafka.api.mytopic;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.sample.kafka.api.mytopic.dto.MyTopicVO;
import spring.sample.mybatis.config.mybatis.Pageable;
import spring.sample.mybatis.config.mybatis.PagedList;

@Mapper
public interface MyTopic1Mapper {
  
  int insert(MyTopicVO data);
  List<MyTopicVO> selectAll();
  PagedList<MyTopicVO> selectList(Pageable param);
  
}
