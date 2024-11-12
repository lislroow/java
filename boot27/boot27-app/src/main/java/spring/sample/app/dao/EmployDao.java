package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.app.vo.EmployVo;
import spring.sample.config.mybatis.Pageable;
import spring.sample.config.mybatis.PagedList;

@Mapper
public interface EmployDao {
  
  List<EmployVo> findAll();
  
  EmployVo findById(@Param("id") String id);
  
  PagedList<EmployVo> findList(Pageable param);
  
  PagedList<EmployVo> findListByName(@Param("vo") EmployVo.FindVo vo);
  
  int add(@Param("vo") EmployVo.AddVo vo);
  
  int modifyNameById(@Param("vo") EmployVo.ModifyVo vo);
  
  int removeById(@Param("vo") EmployVo.RemoveVo vo);
  
}
