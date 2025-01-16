package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.OpenapiAuthVo;

@Mapper
public interface OpenapiAuthDao {
  
  public Optional<OpenapiAuthVo> selectByLoginId(String loginId);
  
  public Optional<OpenapiAuthVo> selectById(String id);
  
}
