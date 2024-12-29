package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import spring.custom.api.vo.ScientistVerticaVo;
import spring.custom.common.annotation.MapperVertica;
import spring.custom.common.mybatis.Pageable;
import spring.custom.common.mybatis.PagedList;

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
