package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.custom.api.vo.ScientistVo;
import spring.custom.common.annotation.MapperH2;
import spring.custom.common.annotation.MapperMaria;
import spring.custom.common.annotation.MapperOracle;
import spring.custom.common.annotation.MapperPostgres;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PagedData;

@MapperH2
@MapperMaria
@MapperOracle
@MapperPostgres
@Mapper
public interface MybatisMultipleDatasourceDao {
  
  List<ScientistVo> findAll();
  
  ScientistVo findById(Integer id);
  
  PagedData<ScientistVo> findList(PageRequest param);
  
  PagedData<ScientistVo> findListByName(ScientistVo.FindVo vo);
  
  int add(ScientistVo.AddVo vo);
  
  int modifyNameById(ScientistVo.ModifyVo vo);
  
  int removeById(ScientistVo.RemoveVo vo);
  
}
