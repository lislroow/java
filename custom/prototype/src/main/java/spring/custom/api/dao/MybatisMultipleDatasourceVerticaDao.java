package spring.custom.api.dao;

import java.util.List;

import spring.custom.api.vo.ScientistVerticaVo;
import spring.custom.common.annotation.MapperVertica;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PagedList;

@MapperVertica
public interface MybatisMultipleDatasourceVerticaDao {
  
  List<ScientistVerticaVo> findAll();
  
  ScientistVerticaVo findById(Integer id);
  
  PagedList<ScientistVerticaVo> findList(PageRequest param);
  
  PagedList<ScientistVerticaVo> findListByName(ScientistVerticaVo.FindVo vo);
  
  int add(ScientistVerticaVo.AddVo vo);
  
  int modifyNameById(ScientistVerticaVo.ModifyVo vo);
  
  int removeById(ScientistVerticaVo.RemoveVo vo);
  
}
