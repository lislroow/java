package spring.custom.api.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface MybatisSampleDao {
  
  List<ScientistVo.SearchResult> allScientists();
  
  Optional<ScientistVo.SearchResult> findScientistById(Integer id);
  
  PageResponse<ScientistVo.SearchResult> findScientists(PageRequest pageRequest);
  
  PageResponse<ScientistVo.SearchResult> searchScientists(
      PageRequest pageRequest, ScientistVo.SearchParam searchVo);
  
  int addScientist(ScientistVo.AddScientist addVo);
  
  int modifyScientistById(ScientistVo.ModifyScientist modifyVo);
  
  int removeScientistById(Integer id);
  
}
