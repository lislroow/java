package spring.redis.service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.redis.RedisSpringMain.FundYmdPrice;
import spring.custom.api.entity.FundIrEntity;
import spring.custom.api.entity.FundMstEntity;
import spring.custom.api.entity.repository.FundIrRepository;
import spring.custom.api.entity.repository.FundMstRepository;

@Service
public class FundIrService {
  
  @Autowired FundMstRepository fundMstRepository;
  @Autowired FundIrRepository fundIrRepository;
  
  @Transactional
  public List<FundYmdPrice> getFundIrs(String fundCd) {
    FundMstEntity result = fundMstRepository.findById(fundCd).get();
    List<FundYmdPrice> listYmdPrice = result.getFundIrs().stream().sorted(Comparator.comparing(FundIrEntity::getBasYmd))
        .map(item -> new FundYmdPrice(
            fundCd,
            item.getBasYmd(),
            item.getFundSizeAmt(),
            item.getFundUnitAmt(),
            item.getBasPrice(),
            item.getTaxBasPrice(),
            item.getIr(),
            item.getBmIr(),
            item.getActIr(),
            item.getIrIdx(),
            item.getBmIrIdx(),
            item.getActIrIdx()))
        .collect(Collectors.toList());
    return listYmdPrice;
  }
  
  @Transactional
  public Map<String, List<FundYmdPrice>> getAllFundIrs(List<String> fundCds) {
    List<FundIrEntity> result = fundIrRepository.findByFundCdIn(fundCds);
    System.out.println("fund-ir size: " + result.size());
    Map<String, List<FundYmdPrice>> groups = result.stream()
        .map(item -> new FundYmdPrice(
            item.getFundCd(),
            item.getBasYmd(),
            item.getFundSizeAmt(),
            item.getFundUnitAmt(),
            item.getBasPrice(),
            item.getTaxBasPrice(),
            item.getIr(),
            item.getBmIr(),
            item.getActIr(),
            item.getIrIdx(),
            item.getBmIrIdx(),
            item.getActIrIdx()))
        .collect(Collectors.groupingBy(FundYmdPrice::fundCd));
    return groups;
  }
}
