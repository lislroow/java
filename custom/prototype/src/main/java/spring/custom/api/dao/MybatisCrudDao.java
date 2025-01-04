package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.mybatis.Pageable;
import spring.custom.common.mybatis.PagedList;

@Mapper
public interface MybatisCrudDao {
  
  List<ScientistVo> findAll();
  
  ScientistVo findById(@Param("id") String id);
  
  PagedList<ScientistVo> findList(Pageable pageable);
  
  PagedList<ScientistVo> findListByName(Pageable pageable, @Param("vo") ScientistVo.FindVo vo);
  
  int add(@Param("vo") ScientistVo.AddVo vo);
  
  int modifyNameById(@Param("vo") ScientistVo.ModifyVo vo);
  
  int removeById(@Param("vo") ScientistVo.RemoveVo vo);
  
}
