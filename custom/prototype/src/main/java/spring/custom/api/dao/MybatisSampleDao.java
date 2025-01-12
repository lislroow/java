package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface MybatisSampleDao {
  
  List<ScientistVo> findAll();
  
  ScientistVo findById(Integer id);
  
  PageResponse<ScientistVo> findList(PageRequest pageRequest);
  
  PageResponse<ScientistVo> findListByName(PageRequest pageRequest, ScientistVo.FindVo vo);
  
  int add(ScientistVo.AddVo vo);
  
  int modifyNameById(ScientistVo.ModifyVo vo);
  
  int removeById(ScientistVo.RemoveVo vo);
  
}
