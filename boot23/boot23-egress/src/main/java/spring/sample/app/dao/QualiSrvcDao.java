package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.app.vo.QualiSrvcDataVo;
import spring.sample.app.vo.QualiSrvcVo;

@Mapper
public interface QualiSrvcDao {
  
  int saveSrvc(QualiSrvcVo vo);
  QualiSrvcVo selectById(@Param("id") String id);
  List<QualiSrvcVo> select(@Param("srvcName") String srvcName, @Param("id") String id);
  
  
  int saveSrvcData(QualiSrvcDataVo vo);
  List<QualiSrvcDataVo> selectDataBySrvcId(@Param("qvServiceId") String qvServiceId);
}
