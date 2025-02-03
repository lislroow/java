package spring.auth.api.dao;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.TokenMngVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface TokenMngDao {

  PageResponse<TokenMngVo.ResultTokenClient> searchClientTokens(
      PageRequest pageRequest, TokenMngVo.SearchParam searchVo);
  
  int addClientToken(TokenMngVo.AddTokenClient addVo);
  
  int modifyClientToken(TokenMngVo.ModifyTokenClient modifyVo);
  
}
