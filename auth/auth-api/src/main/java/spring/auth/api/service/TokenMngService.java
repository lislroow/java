package spring.auth.api.service;

import java.time.LocalDate;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.TokenMngDao;
import spring.auth.api.vo.TokenMngVo;
import spring.auth.common.login.TokenService;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.vo.ClientVo;

@Service
@RequiredArgsConstructor
public class TokenMngService {
  
  final TokenMngDao tokenMngDao;
  final TokenService tokenService;
  
  @Transactional
  public void addClientToken(TokenMngVo.AddTokenClient addVo) {
    ClientVo principal = ClientVo.builder()
        .clientId(addVo.getClientId())
        .roles(addVo.getRoles())
        .clientName(addVo.getClientName())
        .build();
    LocalDate expDate = addVo.getExpDate();
    
    Map.Entry<String, String> result = tokenService.createPtk(TOKEN.USER.CLIENT, expDate, principal);
    addVo.setTokenKey(result.getKey());
    addVo.setTokenValue(result.getValue());
    tokenMngDao.addClientToken(addVo);
  }
  
  @Transactional
  public int modifyClientToken(TokenMngVo.ModifyTokenClient modifyVo) {
    return tokenMngDao.modifyClientToken(modifyVo);
  }
  
}
