package spring.auth.api.controller;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.TokenMngDao;
import spring.auth.api.dto.TokenMngDto;
import spring.auth.api.vo.TokenMngVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@RestController
@RequiredArgsConstructor
public class TokenMngController {

  final ModelMapper modelMapper;
  final TokenMngDao tokenMngDao;
  
  @GetMapping("/v1/token-mng/search")
  public PageResponse<TokenMngDto.TokenRes> searchTokens(
      @RequestParam(required = false) String tokenId,
      @RequestParam(required = false) String clientId,
      @RequestParam(required = false) YN enableYn,
      @RequestParam(required = false) YN lockedYn,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    TokenMngVo.SearchVo searchVo = TokenMngVo.SearchVo.builder()
        .tokenId(tokenId)
        .clientId(clientId)
        .enableYn(enableYn)
        .lockedYn(lockedYn)
        .build();
    PageResponse<TokenMngVo> result = tokenMngDao.searchTokens(PageRequest.of(page, size), searchVo);
    
    PageResponse<TokenMngDto.TokenRes> resDto = new PageResponse<TokenMngDto.TokenRes>(
        result.stream()
          .map(item -> modelMapper.map(item, TokenMngDto.TokenRes.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
}
