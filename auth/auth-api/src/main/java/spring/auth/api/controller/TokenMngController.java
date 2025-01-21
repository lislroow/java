package spring.auth.api.controller;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.TokenMngDao;
import spring.auth.api.dto.TokenMngDto;
import spring.auth.api.service.TokenMngService;
import spring.auth.api.vo.TokenMngVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@RestController
@RequiredArgsConstructor
public class TokenMngController {

  final ModelMapper modelMapper;
  final TokenMngDao tokenMngDao;
  final TokenMngService tokenMngService;
  
  @GetMapping("/v1/token-mng/search")
  public PageResponse<TokenMngDto.ClientTokenRes> searchClientTokens(
      @RequestParam(required = false) String clientId,
      @RequestParam(required = false) String tokenKey,
      @RequestParam(required = false) String contactName,
      @RequestParam(required = false) YN enableYn,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    TokenMngVo.SearchParam searchVo = TokenMngVo.SearchParam.builder()
        .clientId(clientId)
        .tokenKey(tokenKey)
        .contactName(contactName)
        .enableYn(enableYn)
        .build();
    PageResponse<TokenMngVo.ResultTokenClient> resultVo = tokenMngDao.searchClientTokens(PageRequest.of(page, size), searchVo);
    
    PageResponse<TokenMngDto.ClientTokenRes> resDto = new PageResponse<TokenMngDto.ClientTokenRes>(
        resultVo.stream()
          .map(item -> modelMapper.map(item, TokenMngDto.ClientTokenRes.class))
          .collect(Collectors.toList())
        , resultVo.getPageInfo());
    return resDto;
  }
  
  @PostMapping("/v1/token-mng/token-client")
  public ResponseEntity<?> addClientToken(
      @RequestBody TokenMngDto.AddTokenClientReq reqDto) {
    TokenMngVo.AddTokenClient addVo = modelMapper.map(reqDto, TokenMngVo.AddTokenClient.class);
    tokenMngService.addClientToken(addVo);
    
    return ResponseEntity.ok().build();
  }
  
  @PutMapping("/v1/token-mng/token-client")
  public ResponseEntity<?> modifyClientToken(
      @RequestBody TokenMngDto.ModifyTokenClientReq reqDto) {
    TokenMngVo.ModifyTokenClient modifyVo = modelMapper.map(reqDto, TokenMngVo.ModifyTokenClient.class);
    int cnt = tokenMngService.modifyClientToken(modifyVo);
    
    if (cnt == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(modifyVo);
    }
  }
}
