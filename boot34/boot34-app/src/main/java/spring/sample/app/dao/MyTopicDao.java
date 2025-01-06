package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PagedData;
import spring.sample.app.vo.EmployVo;

@Mapper
public interface MyTopicDao {
  
  int insert(EmployVo data);
  
  List<EmployVo> selectAll();
  
  PagedData<EmployVo> selectList(PageRequest param);
  
}
