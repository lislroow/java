package spring.auth.api.controller;

import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import spring.auth.api.dao.UserMngDao;
import spring.auth.api.dto.UserMngDto;
import spring.auth.api.service.UserMngService;
import spring.auth.api.vo.UserMngVo;
import spring.custom.common.enumcode.ERROR;
import spring.custom.common.enumcode.TOKEN;
import spring.custom.common.enumcode.YN;
import spring.custom.common.exception.AppException;
import spring.custom.common.exception.data.DataNotFoundException;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;
import spring.custom.common.redis.RedisSupport;
import spring.custom.common.util.HashUtil;

@RestController
@RequiredArgsConstructor
public class UserMngController {

  final ModelMapper modelMapper;
  final UserMngService userMngService;
  final UserMngDao userMngDao;
  final BCryptPasswordEncoder bcryptPasswordEncoder;
  
  final JavaMailSender mailSender;
  final ObjectMapper objectMapper;
  final RedisSupport redisSupport;
  
  @GetMapping("/v1/user-mng/managers/all")
  public UserMngDto.ManagerListRes allManagers() {
    List<UserMngVo.ResultManager> result = userMngDao.allManagers();
    
    UserMngDto.ManagerListRes resDto = new UserMngDto.ManagerListRes(
        result.stream()
          .map(item -> modelMapper.map(item, UserMngDto.ManagerRes.class))
          .collect(Collectors.toList()));
    return resDto;
  }
  
  @GetMapping("/v1/user-mng/managers")
  public PageResponse<UserMngDto.ManagerRes> findManagers(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    PageResponse<UserMngVo.ResultManager> result = userMngDao.findManagers(PageRequest.of(page, size));
    
    PageResponse<UserMngDto.ManagerRes> resDto = new PageResponse<UserMngDto.ManagerRes>(
        result.stream()
          .map(item -> modelMapper.map(item, UserMngDto.ManagerRes.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/user-mng/managers/search")
  public PageResponse<UserMngDto.ManagerRes> searchManagers(
      @RequestParam(required = false) String loginId,
      @RequestParam(required = false) String mgrName,
      @RequestParam(required = false) String roles,
      @RequestParam(required = false) YN enableYn,
      @RequestParam(required = false) YN lockedYn,
      @RequestParam(required = false, defaultValue = "1") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    UserMngVo.SearchParam searchVo = UserMngVo.SearchParam.builder()
        .loginId(loginId)
        .mgrName(mgrName)
        .roles(roles)
        .enableYn(enableYn)
        .lockedYn(lockedYn)
        .build();
    PageResponse<UserMngVo.ResultManager> result = userMngDao.searchManagers(PageRequest.of(page, size), searchVo);
    
    PageResponse<UserMngDto.ManagerRes> resDto = new PageResponse<UserMngDto.ManagerRes>(
        result.stream()
          .map(item -> modelMapper.map(item, UserMngDto.ManagerRes.class))
          .collect(Collectors.toList())
        , result.getPageInfo());
    return resDto;
  }
  
  @GetMapping("/v1/user-mng/manager/{id}")
  public ResponseEntity<UserMngDto.ManagerRes> findManagerById(
      @PathVariable String id) {
    UserMngVo.ResultManager result = userMngDao.findManagerById(id)
        .orElseThrow(() -> new DataNotFoundException());;
    UserMngDto.ManagerRes resDto = modelMapper.map(result, UserMngDto.ManagerRes.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/user-mng/manager/registration/send")
  public ResponseEntity<?> sendRegisterCode(@RequestBody UserMngDto.SendRegistrationReq reqDto) {
    // redis 저장
    UserMngVo.AddManager addVo = UserMngVo.AddManager.builder()
        .loginId(reqDto.getToEmail())
        .mgrName(reqDto.getToName())
        .roles(reqDto.getGrantRoles())
        .build();
    
    String addVoJson;
    try {
      addVoJson = objectMapper.writeValueAsString(addVo);
    } catch (JsonProcessingException e) {
      throw new AppException(ERROR.A015.code(), e);
    }
    
    String registerCode;
    try {
      registerCode = HashUtil.sha256(addVoJson);
    } catch (NoSuchAlgorithmException e) {
      throw new AppException(ERROR.A015.code(), e);
    }
    Map.Entry<String, String> redisEntry = new AbstractMap.SimpleEntry<>(
        "user:register-code:"+registerCode, addVoJson);
    this.redisSupport.setValue(redisEntry.getKey(), redisEntry.getValue(), Duration.ofDays(1));
    
    // email 발송
    String subject = String.format("[develop] '%s' manager registeration code", reqDto.getToName());
    String text = String.format("http://localhost/user/manager-register/%s", registerCode);
    
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(reqDto.getToEmail());
    message.setSubject(subject);
    message.setText(text);
    message.setFrom("lislroow@daum.net");
    mailSender.send(message);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }
  
  @PostMapping("/v1/user-mng/manager/registration")
  public ResponseEntity<?> registeration(@RequestBody UserMngDto.RegisterationReq reqDto) {
    if (!reqDto.getNewLoginPwd().equals(reqDto.getConfirmLoginPwd())) {
      throw new AppException(ERROR.A014);
    }
    String addVoJson = this.redisSupport.getValueAndDelete(
        "user:register-code:"+reqDto.getRegisterCode());
    if (ObjectUtils.isEmpty(addVoJson)) {
      throw new AppException(ERROR.A016);
    }
    UserMngVo.AddManager addVo = null;
    try {
      addVo = objectMapper.readValue(addVoJson, UserMngVo.AddManager.class);
    } catch (JsonMappingException e) {
      throw new AppException(ERROR.A016, e);
    } catch (JsonProcessingException e) {
      throw new AppException(ERROR.A016, e);
    }
    
    Optional<UserMngVo.ResultManager> optUserMngVo = userMngDao.findManagerByLoginId(addVo.getLoginId());
    if (optUserMngVo.isPresent()) {
      throw new AppException(ERROR.A018.code(), "'"+addVo.getLoginId() + "'" + ERROR.A018.message());
    }
    
    String id = userMngDao.selectNextId(TOKEN.USER_TYPE.MANAGER.idprefix());
    addVo.setId(id);
    addVo.setLoginPwd(bcryptPasswordEncoder.encode(reqDto.getNewLoginPwd()));
    addVo.setEnableYn(YN.Y);
    addVo.setLockedYn(YN.N);
    addVo.setPwdExpDate(LocalDate.now().plusDays(90));
    addVo.setCreateId(id);
    addVo.setModifyId(id);
    userMngDao.addManager(addVo);
    return ResponseEntity.ok().build();
  }
  
  @PutMapping("/v1/user-mng/manager")
  public ResponseEntity<?> modifyManagerById(
      @RequestBody UserMngDto.ModifyManagerReq reqDto) {
    UserMngVo.ModifyManager modifyVo = modelMapper.map(reqDto, UserMngVo.ModifyManager.class);
    
    int result = userMngService.modifyManagerById(modifyVo);
    
    if (result == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(modifyVo);
    }
  }
  
  @PutMapping("/v1/user-mng/manager/password/change")
  public ResponseEntity<?> changeManagerPasswordById(
      @RequestBody UserMngDto.ChangePasswordReq reqDto) {
    Optional<String> optLoginPwd = userMngDao.findManagerLoginPwdById(reqDto.getId());
    if (optLoginPwd.isPresent()) {
      String loginPwd = optLoginPwd.get().substring(optLoginPwd.get().indexOf("}")+1);
      if (!bcryptPasswordEncoder.matches(reqDto.getCurrentLoginPwd(), loginPwd)) {
        throw new AppException(ERROR.A013);
      }
    }
    
    if (!reqDto.getNewLoginPwd().equals(reqDto.getConfirmLoginPwd())) {
      throw new AppException(ERROR.A014);
    }
    
    String id = reqDto.getId();
    String newLoginPwd = bcryptPasswordEncoder.encode(reqDto.getNewLoginPwd());
    int result = userMngService.changeManagerLoginPwdById(id, newLoginPwd);
    
    if (result == 0) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok().build();
    }
  }
  
  @DeleteMapping("/v1/user-mng/manager/{id}")
  public ResponseEntity<?> removeManagerById(
      @PathVariable String id) {
    userMngService.removeManagerById(id);
    
    return ResponseEntity.noContent().build();
  }
  
}
