package spring.custom.api.controller;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dto.FundDto;
import spring.custom.api.entity.FundMstEntity;
import spring.custom.api.entity.repository.FundMstRepository;
import spring.custom.api.entity.spec.FundMstSpecification;

@RestController
@RequiredArgsConstructor
public class FundController {

  final ModelMapper modelMapper;
  final FundMstRepository fundMstRepository;
  
  
  @GetMapping("/v1/fund/fund-mst/search")
  public Page<FundDto.FundMstRes> searchFundMsts(
      @RequestParam(required = false) String fundFnm,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Specification<FundMstEntity> spec = 
        Specification.where(FundMstSpecification.hasFundFnm(fundFnm));
    Page<FundMstEntity> result = fundMstRepository.findAll(spec, PageRequest.of(page, size));
    return result.map(item -> modelMapper.map(item, FundDto.FundMstRes.class));
  }
  
}
