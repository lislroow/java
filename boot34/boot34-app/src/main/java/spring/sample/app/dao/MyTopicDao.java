package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.common.mybatis.Pageable;
import spring.custom.common.mybatis.PagedList;
import spring.sample.app.vo.EmployVo;

@Mapper
public interface MyTopicDao {
  
  int insert(EmployVo data);
  
  List<EmployVo> selectAll();
  
  PagedList<EmployVo> selectList(Pageable param);
  
}
