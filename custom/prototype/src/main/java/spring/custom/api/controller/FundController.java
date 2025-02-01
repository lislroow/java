package spring.custom.api.controller;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

  final ModelMapper modelMapper;
  @Nullable final FundMstRepository fundMstRepository;
  
  
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
        .and(FundMstSpecification.hasFundFnm(fundFnm));
    Page<FundMstEntity> result = fundMstRepository.findAll(spec, PageRequest.of(page, size));
    return result.map(item -> modelMapper.map(item, FundDto.FundMstRes.class));
  }
  
  @GetMapping("/v1/fund/ir/line-chart/{fundCd}")
  public List<FundDto.FundIrRes> findIrLineChart(
      @PathVariable String fundCd) {
    FundMstEntity result = fundMstRepository.findById(fundCd).orElseThrow(DataNotFoundException::new);
    return result.getFundIrs().stream()
        .sorted(Comparator.comparing(FundIrEntity::getBasYmd))
        .map(item -> modelMapper.map(item, FundDto.FundIrRes.class))
        .collect(Collectors.toList());
  }
  
}
