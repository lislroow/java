package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PagedData;

@Mapper
public interface MybatisCrudDao {
  
  List<ScientistVo> findAll();
  
  ScientistVo findById(Integer id);
  
  PagedData<ScientistVo> findList(PageRequest pageRequest);
  
  PagedData<ScientistVo> findListByName(PageRequest pageRequest, ScientistVo.FindVo vo);
  
  int add(ScientistVo.AddVo vo);
  
  int modifyNameById(ScientistVo.ModifyVo vo);
  
  int removeById(ScientistVo.RemoveVo vo);
  
}
