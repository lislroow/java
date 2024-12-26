package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.sample.app.vo.EmployVo;
import spring.component.common.mybatis.Pageable;
import spring.component.common.mybatis.PagedList;

@Mapper
public interface MyTopicDao {
  
  int insert(EmployVo data);
  
  List<EmployVo> selectAll();
  
  PagedList<EmployVo> selectList(Pageable param);
  
}
