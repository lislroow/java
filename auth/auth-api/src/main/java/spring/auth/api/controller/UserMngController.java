package spring.auth.api.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.UserMngDao;
import spring.auth.api.dto.UserMngReqDto;
import spring.auth.api.dto.UserMngResDto;
import spring.auth.api.service.UserMngService;
import spring.auth.api.vo.UserMngVo;
import spring.custom.common.enumcode.YN;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@RestController
@RequiredArgsConstructor
public class UserMngController {

  final ModelMapper modelMapper;
  final UserMngService userMngService;
  final UserMngDao userMngDao;
  
  @GetMapping("/v1/user-mng/managers/all")
  public UserMngResDto.ManagerList allManagers() {
    List<UserMngVo> result = userMngDao.allManagers();
    
    UserMngResDto.ManagerList resDto = new UserMngResDto.ManagerList(
        result.stream()
          .map(item -> modelMapper.map(item, UserMngResDto.Manager.class))
          .collect(Collectors.toList()));
    return resDto;
  }
  
  @GetMapping("/v1/user-mng/managers")
  public PageResponse<UserMngResDto.Manager> findManagers(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    PageResponse<UserMngVo> result = userMngDao.findManagers(PageRequest.of(page, size));
    
    PageResponse<UserMngResDto.Manager> resDto = new PageResponse<UserMngResDto.Manager>(
        result.stream()
          .map(item -> modelMapper.map(item, UserMngResDto.Manager.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/user-mng/managers/search")
  public PageResponse<UserMngResDto.Manager> searchManagers(
      @RequestParam(required = false) String loginId,
      @RequestParam(required = false) String mgrName,
      @RequestParam(required = false) String role,
      @RequestParam(required = false) YN disabledYn,
      @RequestParam(required = false) YN lockedYn,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    UserMngVo.SearchVo searchVo = UserMngVo.SearchVo.builder()
        .loginId(loginId)
        .mgrName(mgrName)
        .role(role)
        .disabledYn(disabledYn)
        .lockedYn(lockedYn)
        .build();
    PageResponse<UserMngVo> result = userMngDao.searchManagers(PageRequest.of(page, size), searchVo);
    
    PageResponse<UserMngResDto.Manager> resDto = new PageResponse<UserMngResDto.Manager>(
        result.stream()
          .map(item -> modelMapper.map(item, UserMngResDto.Manager.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/user-mng/manager/{id}")
  public ResponseEntity<UserMngResDto.Manager> findManagerById(
      @PathVariable Integer id) {
    UserMngVo result = userMngDao.findManagerById(id);
    UserMngResDto.Manager resDto = modelMapper.map(result, UserMngResDto.Manager.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/user-mng/manager")
  public ResponseEntity<?> addManager(
      @RequestBody UserMngReqDto.AddManager reqDto) {
    UserMngVo.AddVo addVo = modelMapper.map(reqDto, UserMngVo.AddVo.class);
    userMngService.addManager(addVo);
    
    return ResponseEntity.status(HttpStatus.CREATED).build(); // location 정보는 JPA 일 때 적합
  }
  
  @PutMapping("/v1/user-mng/manager")
  public ResponseEntity<?> modifyManagerById(
      @RequestBody UserMngReqDto.ModifyManager reqDto) {
    UserMngVo.ModifyVo modifyVo = modelMapper.map(reqDto, UserMngVo.ModifyVo.class);
    int result = userMngService.modifyManagerById(modifyVo);
    
    if (result == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(modifyVo);
    }
  }
  
  @DeleteMapping("/v1/user-mng/manager/{id}")
  public ResponseEntity<?> removeManagerById(
      @PathVariable Integer id) {
    userMngService.removeManagerById(id);
    
    return ResponseEntity.noContent().build();
  }
  
}
