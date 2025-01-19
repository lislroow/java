package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.CommonCodeVo;

@Mapper
public interface CommonCodeDao {
  
  List<CommonCodeVo.AllCodeVo> allCodes();
  
  List<CommonCodeVo.CodeVo> findCodesByCdGrp(String cdGrp);
  
}
