package spring.sample.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.app.vo.AccessControlVo;

@Mapper
public interface AccessControlDao {

  int insert(@Param("ipAddr") String ipAddr, @Param("delayTime") Long delayTime);
  
  List<AccessControlVo> selectByIp(@Param("ipAddr") String ipAddr);
  
}
