package spring.redis.service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.custom.api.dto.FundDto;
import spring.custom.api.entity.FundIrEntity;
import spring.custom.api.entity.repository.FundIrRepository;
import spring.custom.api.entity.repository.FundMstRepository;

@Service
@Transactional(readOnly = true)
public class FundIrService {
  
  @Autowired FundMstRepository fundMstRepository;
  @Autowired FundIrRepository fundIrRepository;
  @Autowired ModelMapper modelMapper;
  
  public Map<String, List<FundDto.FundIrRes>> getAllFundIrs(List<String> fundCds) {
    List<FundIrEntity> result = fundIrRepository.findByFundCdIn(fundCds);
    System.out.println(String.format("  fundIrs: %d, fundCds: %d", fundCds.size(), result.size()));
    Map<String, List<FundDto.FundIrRes>> groups = result.stream()
        .map(item -> modelMapper.map(item, FundDto.FundIrRes.class))
        .collect(Collectors.groupingBy(FundDto.FundIrRes::getFundCd));
    return groups;
  }
}
