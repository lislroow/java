package spring.custom.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.custom.api.dao.MybatisMultipleDatasourceDao;
import spring.custom.api.dao.MybatisMultipleDatasourceVerticaDao;
import spring.custom.api.dto.MybatisMultipleDatasourceDto;
import spring.custom.api.vo.ScientistVerticaVo;
import spring.custom.api.vo.ScientistVo;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MybatisMultipleDatasourceController {

  final ModelMapper modelMapper;
  
  @Autowired(required = false)
  @Qualifier(value = "mybatisMultipleDatasourceDao")
  private MybatisMultipleDatasourceDao dao;

  @Autowired(required = false)
  @Qualifier(value = "mybatisMultipleDatasourceH2Dao")
  private MybatisMultipleDatasourceDao h2Dao;
  
  @Autowired(required = false)
  @Qualifier(value = "mybatisMultipleDatasourceMariaDao")
  private MybatisMultipleDatasourceDao mariaDao;
  
  @Autowired(required = false)
  @Qualifier(value = "mybatisMultipleDatasourceOracleDao")
  private MybatisMultipleDatasourceDao oracleDao;
  
  @Autowired(required = false)
  private MybatisMultipleDatasourceVerticaDao verticaDao;
  
  @Autowired(required = false)
  @Qualifier(value = "mybatisMultipleDatasourcePostgresDao")
  private MybatisMultipleDatasourceDao postgresDao;
  
  @GetMapping("/v1/mybatis-multiple-datasource/all")
  public ResponseEntity<MybatisMultipleDatasourceDto.ScientistListRes> allScientists() {
    MybatisMultipleDatasourceDto.ScientistListRes resDto = new MybatisMultipleDatasourceDto.ScientistListRes();
    List<MybatisMultipleDatasourceDto.ScientistRes> listAll = new ArrayList<MybatisMultipleDatasourceDto.ScientistRes>();
    if (h2Dao != null)
      listAll.addAll(h2Dao.allScientists().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
          .collect(Collectors.toList()));
    else
      log.warn("h2Dao is null");
    
    if (mariaDao != null)
      listAll.addAll(mariaDao.allScientists().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
          .collect(Collectors.toList()));
    else
      log.warn("mariaDao is null");
    
    if (oracleDao != null)
      listAll.addAll(oracleDao.allScientists().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
          .collect(Collectors.toList()));
    else
      log.warn("oracleDao is null");
    
    if (verticaDao != null)
      listAll.addAll(verticaDao.allScientists().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
          .collect(Collectors.toList()));
    else
      log.warn("verticaDao is null");
    
    if (postgresDao != null)
      listAll.addAll(postgresDao.allScientists().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
          .collect(Collectors.toList()));
    else
      log.warn("postgresDao is null");
    
      resDto.setList(listAll);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/standard")
  public ResponseEntity<MybatisMultipleDatasourceDto.ScientistListRes> standard() {
    List<ScientistVo> result = dao.allScientists();
    List<MybatisMultipleDatasourceDto.ScientistRes> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceDto.ScientistListRes resDto = new MybatisMultipleDatasourceDto.ScientistListRes();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/h2")
  public ResponseEntity<MybatisMultipleDatasourceDto.ScientistListRes> h2() {
    List<ScientistVo> result = h2Dao.allScientists();
    List<MybatisMultipleDatasourceDto.ScientistRes> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceDto.ScientistListRes resDto = new MybatisMultipleDatasourceDto.ScientistListRes();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/maria")
  public ResponseEntity<MybatisMultipleDatasourceDto.ScientistListRes> maria() {
    List<ScientistVo> result = mariaDao.allScientists();
    List<MybatisMultipleDatasourceDto.ScientistRes> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceDto.ScientistListRes resDto = new MybatisMultipleDatasourceDto.ScientistListRes();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/oracle")
  public ResponseEntity<MybatisMultipleDatasourceDto.ScientistListRes> oracle() {
    List<ScientistVo> result = oracleDao.allScientists();
    List<MybatisMultipleDatasourceDto.ScientistRes> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceDto.ScientistListRes resDto = new MybatisMultipleDatasourceDto.ScientistListRes();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/vertica")
  public ResponseEntity<MybatisMultipleDatasourceDto.ScientistListRes> vertica() {
    List<ScientistVerticaVo> result = verticaDao.allScientists();
    List<MybatisMultipleDatasourceDto.ScientistRes> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceDto.ScientistListRes resDto = new MybatisMultipleDatasourceDto.ScientistListRes();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/postgres")
  public ResponseEntity<MybatisMultipleDatasourceDto.ScientistListRes> postgres() {
    List<ScientistVo> result = postgresDao.allScientists();
    List<MybatisMultipleDatasourceDto.ScientistRes> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceDto.ScientistRes.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceDto.ScientistListRes resDto = new MybatisMultipleDatasourceDto.ScientistListRes();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
}
