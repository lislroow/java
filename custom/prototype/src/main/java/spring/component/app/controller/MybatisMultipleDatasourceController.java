package spring.component.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.component.app.dao.MybatisMultipleDatasourceDao;
import spring.component.app.dao.MybatisMultipleDatasourceVerticaDao;
import spring.component.app.dto.MybatisMultipleDatasourceResDto;
import spring.component.app.vo.ScientistVerticaVo;
import spring.component.app.vo.ScientistVo;
import spring.custom.common.dto.ResponseDto;

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
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> all() {
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
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/standard")
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> standard() {
    List<ScientistVo> result = dao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/h2")
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> h2() {
    List<ScientistVo> result = h2Dao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/maria")
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> maria() {
    List<ScientistVo> result = mariaDao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/oracle")
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> oracle() {
    List<ScientistVo> result = oracleDao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/vertica")
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> vertica() {
    List<ScientistVerticaVo> result = verticaDao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis-multiple-datasource/postgres")
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> postgres() {
    List<ScientistVo> result = postgresDao.findAll();
    List<MybatisMultipleDatasourceResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList());
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
}
