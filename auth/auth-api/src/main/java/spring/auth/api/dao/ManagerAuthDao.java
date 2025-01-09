package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.auth.api.vo.ManagerAuthVo;

@Mapper
public interface ManagerAuthDao {
  
  public Optional<ManagerAuthVo> selectById(@Param("mgrId") String mgrId);
  
}
