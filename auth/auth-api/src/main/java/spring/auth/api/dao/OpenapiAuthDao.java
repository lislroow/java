package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.auth.api.vo.OpenapiAuthVo;

@Mapper
public interface OpenapiAuthDao {
  
  public Optional<OpenapiAuthVo> selectById(@Param("userId") String userId);
  
}
