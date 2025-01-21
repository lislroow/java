package spring.custom.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.custom.api.vo.CommonCodeVo;

@Mapper
public interface CommonCodeDao {
  
  List<CommonCodeVo.ResultAllCode> allCodes();
  
  List<CommonCodeVo.ResultCode> findCodesByCdGrp(String cdGrp);
  
}
