package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.TokenVo;

@Mapper
public interface TokenDao {
  
  Optional<TokenVo> findByTokenId(String tokenId);
  
  int insert(TokenVo tokenVo);
  
}
