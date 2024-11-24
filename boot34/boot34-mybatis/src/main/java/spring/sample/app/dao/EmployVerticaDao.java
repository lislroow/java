package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import spring.sample.app.vo.EmployVerticaVo;
import spring.sample.common.annotation.MapperVertica;
import spring.sample.common.mybatis.Pageable;
import spring.sample.common.mybatis.PagedList;

@MapperVertica
public interface EmployVerticaDao {
  
  List<EmployVerticaVo> findAll();
  
  EmployVerticaVo findById(@Param("id") String id);
  
  PagedList<EmployVerticaVo> findList(Pageable param);
  
  PagedList<EmployVerticaVo> findListByName(@Param("vo") EmployVerticaVo.FindVo vo);
  
  int add(@Param("vo") EmployVerticaVo.AddVo vo);
  
  int modifyNameById(@Param("vo") EmployVerticaVo.ModifyVo vo);
  
  int removeById(@Param("vo") EmployVerticaVo.RemoveVo vo);
  
}
