package spring.custom.api.dao;

import java.util.List;

import spring.custom.api.vo.ScientistVerticaVo;
import spring.custom.common.annotation.MapperVertica;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PagedData;

@MapperVertica
public interface MybatisMultipleDatasourceVerticaDao {
  
  List<ScientistVerticaVo> findAll();
  
  ScientistVerticaVo findById(Integer id);
  
  PagedData<ScientistVerticaVo> findList(PageRequest param);
  
  PagedData<ScientistVerticaVo> findListByName(ScientistVerticaVo.FindVo vo);
  
  int add(ScientistVerticaVo.AddVo vo);
  
  int modifyNameById(ScientistVerticaVo.ModifyVo vo);
  
  int removeById(ScientistVerticaVo.RemoveVo vo);
  
}
