package spring.component.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import spring.component.app.vo.ScientistVerticaVo;
import spring.component.common.annotation.MapperVertica;
import spring.component.common.mybatis.Pageable;
import spring.component.common.mybatis.PagedList;

@MapperVertica
public interface MybatisMultipleDatasourceVerticaDao {
  
  List<ScientistVerticaVo> findAll();
  
  ScientistVerticaVo findById(@Param("id") String id);
  
  PagedList<ScientistVerticaVo> findList(Pageable param);
  
  PagedList<ScientistVerticaVo> findListByName(@Param("vo") ScientistVerticaVo.FindVo vo);
  
  int add(@Param("vo") ScientistVerticaVo.AddVo vo);
  
  int modifyNameById(@Param("vo") ScientistVerticaVo.ModifyVo vo);
  
  int removeById(@Param("vo") ScientistVerticaVo.RemoveVo vo);
  
}
