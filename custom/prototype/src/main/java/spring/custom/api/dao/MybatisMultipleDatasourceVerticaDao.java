package spring.custom.api.dao;

import java.util.List;

import spring.custom.api.vo.ScientistVerticaVo;
import spring.custom.api.vo.ScientistVo;
import spring.custom.common.annotation.MapperVertica;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@MapperVertica
public interface MybatisMultipleDatasourceVerticaDao {
  
  List<ScientistVerticaVo> allScientists();
  
  ScientistVerticaVo findScientistById(Integer id);
  
  PageResponse<ScientistVerticaVo> findScientists(PageRequest pageRequest);
  
  PageResponse<ScientistVerticaVo> searchScientists(
      PageRequest pageRequest, ScientistVo.SearchParam searchVo);
  
  int addScientist(ScientistVerticaVo.AddVo addVo);
  
  int modifyScientistById(ScientistVerticaVo.ModifyVo modifyVo);
  
  int removeScientistById(Integer id);
  
}
