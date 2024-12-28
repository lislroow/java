package spring.sample.app.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.common.dto.ResponseDto;
import spring.sample.app.dao.CardStatsDao;
import spring.sample.app.dto.CardStatsReqDto;
import spring.sample.app.dto.CardStatsResDto;
import spring.sample.app.vo.CardPaymentVo;
import spring.sample.app.vo.CardVo;
import spring.sample.common.annotation.AccessControl;

@RestController
@RequiredArgsConstructor
public class CardStatsController {

  final ModelMapper modelMapper;
  final CardStatsDao cardStatsDao;
  
  @PostMapping("/v1/card-stats/issue-info")
  @AccessControl
  public ResponseDto<CardStatsResDto.IssueInfo> getIssueInfo(
      @RequestBody CardStatsReqDto.Issue request) {
    Optional<CardVo> result = cardStatsDao.selectIssueDateByCardNo(request.getCardNo());
    
    CardStatsResDto.IssueInfo resDto = null;
    if (result.isPresent()) {
      resDto = modelMapper.map(result, CardStatsResDto.IssueInfo.class);
    }
    
    return ResponseDto.body(resDto);
  }
  
  @PostMapping("/v1/card-stats/payments")
  @AccessControl
  public ResponseDto<CardStatsResDto.Payments> getPayments(
      @RequestBody CardStatsReqDto.Payment request) {
    List<CardPaymentVo> result = cardStatsDao.selectPaymentsByCardNo(request.getCardNo());
    
    CardStatsResDto.Payments resDto = new CardStatsResDto.Payments();
    if (result.size() > 0) {
      resDto.setPayments(result.stream()
          .map(item -> modelMapper.map(item, CardStatsResDto.PaymentDetail.class))
          .collect(Collectors.toList()));
    }
    
    return ResponseDto.body(resDto);
  }
  
}
