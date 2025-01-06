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
import spring.custom.api.dto.MybatisMultipleDatasourceResDto;
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
  public ResponseEntity<MybatisMultipleDatasourceResDto.ScientistList> all() {
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    List<MybatisMultipleDatasourceResDto.Scientist> listAll = new ArrayList<MybatisMultipleDatasourceResDto.Scientist>();
    if (h2Dao != null)
      listAll.addAll(h2Dao.findAll().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
          .collect(Collectors.toList()));
    else
      log.warn("h2Dao is null");
    
    if (mariaDao != null)
      listAll.addAll(mariaDao.findAll().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
          .collect(Collectors.toList()));
    else
      log.warn("mariaDao is null");
    
    if (oracleDao != null)
      listAll.addAll(oracleDao.findAll().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
          .collect(Collectors.toList()));
    else
      log.warn("oracleDao is null");
    
    if (verticaDao != null)
      listAll.addAll(verticaDao.findAll().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
          .collect(Collectors.toList()));
    else
      log.warn("verticaDao is null");
    
    if (postgresDao != null)
      listAll.addAll(postgresDao.findAll().stream()
          .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
          .collect(Collectors.toList()));
    else
      log.warn("postgresDao is null");
    
      resDto.setList(listAll);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/standard")
  public ResponseEntity<MybatisMultipleDatasourceResDto.ScientistList> standard() {
    List<ScientistVo> result = dao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/h2")
  public ResponseEntity<MybatisMultipleDatasourceResDto.ScientistList> h2() {
    List<ScientistVo> result = h2Dao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/maria")
  public ResponseEntity<MybatisMultipleDatasourceResDto.ScientistList> maria() {
    List<ScientistVo> result = mariaDao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/oracle")
  public ResponseEntity<MybatisMultipleDatasourceResDto.ScientistList> oracle() {
    List<ScientistVo> result = oracleDao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/vertica")
  public ResponseEntity<MybatisMultipleDatasourceResDto.ScientistList> vertica() {
    List<ScientistVerticaVo> result = verticaDao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/postgres")
  public ResponseEntity<MybatisMultipleDatasourceResDto.ScientistList> postgres() {
    List<ScientistVo> result = postgresDao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseEntity.ok(resDto);
  }
}
