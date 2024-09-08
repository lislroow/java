package spring.sample.egress.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.egress.vo.QualiSrvcDataVo;
import spring.sample.egress.vo.QualiSrvcVo;

@Mapper
public interface QualiSrvcDao {
  
  int saveSrvc(QualiSrvcVo vo);
  QualiSrvcVo selectById(@Param("id") String id);
  List<QualiSrvcVo> select(@Param("srvcName") String srvcName, @Param("id") String id);
  
  
  int saveSrvcData(QualiSrvcDataVo vo);
  List<QualiSrvcDataVo> selectDataBySrvcId(@Param("qvServiceId") String qvServiceId);
}
