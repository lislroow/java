package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import spring.sample.app.vo.ScientistVerticaVo;
import spring.sample.common.annotation.MapperVertica;
import spring.sample.common.mybatis.Pageable;
import spring.sample.common.mybatis.PagedList;

@MapperVertica
public interface ScientistVerticaDao {
  
  List<ScientistVerticaVo> findAll();
  
  ScientistVerticaVo findById(@Param("id") String id);
  
  PagedList<ScientistVerticaVo> findList(Pageable param);
  
  PagedList<ScientistVerticaVo> findListByName(@Param("vo") ScientistVerticaVo.FindVo vo);
  
  int add(@Param("vo") ScientistVerticaVo.AddVo vo);
  
  int modifyNameById(@Param("vo") ScientistVerticaVo.ModifyVo vo);
  
  int removeById(@Param("vo") ScientistVerticaVo.RemoveVo vo);
  
}
