package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface MybatisSampleDao {
  
  List<ScientistVo> allScientists();
  
  ScientistVo findScientistById(Integer id);
  
  PageResponse<ScientistVo> findScientists(PageRequest pageRequest);
  
  PageResponse<ScientistVo> searchScientists(PageRequest pageRequest, ScientistVo.SearchVo searchVo);
  
  int addScientist(ScientistVo.AddVo addVo);
  
  int modifyScientistById(ScientistVo.ModifyVo modifyVo);
  
  int removeScientistById(Integer id);
  
}
