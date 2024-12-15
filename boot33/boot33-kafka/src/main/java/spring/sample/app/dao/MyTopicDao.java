package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.sample.app.vo.MyTopicVo;
import spring.sample.common.mybatis.Pageable;
import spring.sample.common.mybatis.PagedList;

@Mapper
public interface MyTopicDao {
  
  int insert(MyTopicVo data);
  
  List<MyTopicVo> selectAll();
  
  PagedList<MyTopicVo> selectList(Pageable param);
  
}
