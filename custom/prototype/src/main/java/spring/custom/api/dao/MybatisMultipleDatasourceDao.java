package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.annotation.MapperH2;
import spring.custom.common.annotation.MapperMaria;
import spring.custom.common.annotation.MapperOracle;
import spring.custom.common.annotation.MapperPostgres;
import spring.custom.common.mybatis.Pageable;
import spring.custom.common.mybatis.PagedList;

@MapperH2
@MapperMaria
@MapperOracle
@MapperPostgres
@Mapper
public interface MybatisMultipleDatasourceDao {
  
  List<ScientistVo> findAll();
  
  ScientistVo findById(@Param("id") String id);
  
  PagedList<ScientistVo> findList(Pageable param);
  
  PagedList<ScientistVo> findListByName(@Param("vo") ScientistVo.FindVo vo);
  
  int add(@Param("vo") ScientistVo.AddVo vo);
  
  int modifyNameById(@Param("vo") ScientistVo.ModifyVo vo);
  
  int removeById(@Param("vo") ScientistVo.RemoveVo vo);
  
}
