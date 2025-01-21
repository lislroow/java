package spring.custom.api.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface MybatisSampleDao {
  
  List<ScientistVo.ResultScientist> allScientists();
  
  Optional<ScientistVo.ResultScientist> findScientistById(Integer id);
  
  PageResponse<ScientistVo.ResultScientist> findScientists(PageRequest pageRequest);
  
  PageResponse<ScientistVo.ResultScientist> searchScientists(
      PageRequest pageRequest, ScientistVo.SearchParam searchVo);
  
  int addScientist(ScientistVo.AddScientist addVo);
  
  int modifyScientistById(ScientistVo.ModifyScientist modifyVo);
  
  int removeScientistById(Integer id);
  
}
