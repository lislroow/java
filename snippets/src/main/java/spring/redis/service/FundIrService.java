package spring.redis.service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import main.redis.RedisSpringMain.FundYmdPrice;
import spring.custom.api.entity.FundIrEntity;
import spring.custom.api.entity.FundMstEntity;
import spring.custom.api.entity.repository.FundMstRepository;

@Service
public class FundIrService {
  
  @Autowired FundMstRepository fundMstRepository;
  
  @Transactional
  public List<FundYmdPrice> getFundIrs(String fundCd) {
    FundMstEntity result = fundMstRepository.findById(fundCd).get();
    List<FundYmdPrice> listYmdPrice = result.getFundIrs().stream().sorted(Comparator.comparing(FundIrEntity::getBasYmd))
        .map(item -> new FundYmdPrice(item.getBasYmd(), item.getBasPrice()))
        .collect(Collectors.toList());
    return listYmdPrice;
  }
}
