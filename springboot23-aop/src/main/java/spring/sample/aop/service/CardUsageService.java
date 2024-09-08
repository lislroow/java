package spring.sample.aop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.aop.dao.CardUsageDao;
import spring.sample.aop.dto.ReqCardDto;
import spring.sample.aop.vo.CardUsageVo;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CardUsageService {
  
  private final CardUsageDao cardUsageDao;
  /**
   * 카드 이용내역 조회
   */
  public List<CardUsageVo> getCardUsageList(ReqCardDto.UsageSearch request) {
    PageHelper.startPage(request.getPageNum(), request.getPageSize());
    return cardUsageDao.selectCardUsageList(request.getName(), request.getCardNo());
  }
  
}