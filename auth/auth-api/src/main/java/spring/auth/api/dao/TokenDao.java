package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.common.login.TokenVo;

@Mapper
public interface TokenDao {
  
  Optional<TokenVo.ClientToken> findClientTokenByTokenKey(String tokenId);
  
}
