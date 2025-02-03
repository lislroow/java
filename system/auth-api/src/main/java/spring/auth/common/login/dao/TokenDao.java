package spring.auth.common.login.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.common.login.vo.TokenVo;

@Mapper
public interface TokenDao {
  
  Optional<TokenVo.ClientToken> findClientTokenByTokenKey(String tokenId);
  
}
