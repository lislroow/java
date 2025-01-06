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
  
  ScientistVo findById(Integer id);
  
  PagedList<ScientistVo> findList(Pageable param);
  
  PagedList<ScientistVo> findListByName(ScientistVo.FindVo vo);
  
  int add(ScientistVo.AddVo vo);
  
  int modifyNameById(ScientistVo.ModifyVo vo);
  
  int removeById(ScientistVo.RemoveVo vo);
  
}
