package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.auth.api.vo.OpendataAuthVo;

@Mapper
public interface OpendataAuthDao {
  
  public Optional<OpendataAuthVo> selectById(@Param("userId") String userId);
  
}
