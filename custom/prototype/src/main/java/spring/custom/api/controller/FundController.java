package spring.custom.api.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import spring.custom.api.dto.FundDto;
import spring.custom.api.entity.FundIrEntity;
import spring.custom.api.entity.FundMstEntity;
import spring.custom.api.entity.repository.FundMstRepository;
import spring.custom.api.entity.spec.FundMstSpecification;
import spring.custom.common.exception.data.DataNotFoundException;

@RestController
@RequiredArgsConstructor
public class FundController {

  final ObjectMapper objectMapper;
  final ModelMapper modelMapper;
  @Nullable final FundMstRepository fundMstRepository;
  @Nullable final RedisTemplate<String, String> redisTemplate;
  ZSetOperations<String, String> zSetOps;
  
  @PostConstruct
  public void init() {
    if (zSetOps == null) zSetOps = redisTemplate.opsForZSet();
  }
  
  @GetMapping("/v1/fund/fund-mst/all")
  public List<FundDto.FundMstRes> allFundMsts() {
    List<FundMstEntity> result = fundMstRepository.findAll();
    return result.stream()
        .sorted(Comparator.comparing(FundMstEntity::getFundFnm))
        .map(item -> modelMapper.map(item, FundDto.FundMstRes.class))
        .collect(Collectors.toList());
  }
  
  @GetMapping("/v1/fund/fund-mst/search")
  public Page<FundDto.FundMstRes> searchFundMsts(
      @RequestParam(required = false) String fundCd,
      @RequestParam(required = false) String fundFnm,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Specification<FundMstEntity> spec = 
        Specification.where(FundMstSpecification.hasFundCd(fundCd))
        .and(FundMstSpecification.hasFundFnm(fundFnm))
        .and((root, query, criteria) -> criteria.equal(root.get("deleted"), false));
    Page<FundMstEntity> result = fundMstRepository.findAll(spec, PageRequest.of(page, size, Sort.by("fundFnm").ascending()));
    return result.map(item -> modelMapper.map(item, FundDto.FundMstRes.class));
  }
  
  @GetMapping("/v1/fund/ir/line-chart/db/{fundCd}")
  public List<FundDto.FundIrRes> findIrLineChartDB(
      @PathVariable String fundCd) {
    FundMstEntity result = fundMstRepository.findById(fundCd).orElseThrow(DataNotFoundException::new);
    return result.getFundIrs().stream()
        .sorted(Comparator.comparing(FundIrEntity::getBasYmd))
        .map(item -> modelMapper.map(item, FundDto.FundIrRes.class))
        .collect(Collectors.toList());
  }
  
  @GetMapping("/v1/fund/ir/line-chart/redis/{fundCd}")
  public List<FundDto.FundIrRes> findIrLineChartRedis(
      @PathVariable String fundCd) {
    String key = "fund:ir:"+fundCd;
    Set<String> result = zSetOps.rangeByScore(key, Double.MIN_VALUE, Double.MAX_VALUE);
    List<FundDto.FundIrRes> resDto = result.stream().map(item -> {
      try {
        return objectMapper.readValue(item, FundDto.FundIrRes.class);
      } catch (JsonProcessingException e) {
        e.printStackTrace();
      }
      return null;
    }).collect(Collectors.toList());
    return resDto;
  }
  
}
