package spring.auth.api.dao;

import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.ClientTokenVo;

@Mapper
public interface TokenDao {
  
  Optional<ClientTokenVo.VerifyToken> findClientTokenByTokenKey(String tokenId);
  
}
