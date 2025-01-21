package spring.custom.api.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface MybatisSampleDao {
  
  List<ScientistVo.ResultVo> allScientists();
  
  Optional<ScientistVo.ResultVo> findScientistById(Integer id);
  
  PageResponse<ScientistVo.ResultVo> findScientists(PageRequest pageRequest);
  
  PageResponse<ScientistVo.ResultVo> searchScientists(
      PageRequest pageRequest, ScientistVo.SearchParam searchVo);
  
  int addScientist(ScientistVo.AddScientist addVo);
  
  int modifyScientistById(ScientistVo.ModifyScientist modifyVo);
  
  int removeScientistById(Integer id);
  
}
