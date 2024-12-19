package spring.sample.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.sample.app.dao.MybatisMultipleDatasourceDao;
import spring.sample.app.dao.MybatisMultipleDatasourceVerticaDao;
import spring.sample.app.dto.MybatisMultipleDatasourceResDto;
import spring.sample.app.vo.ScientistVerticaVo;
import spring.sample.app.vo.ScientistVo;
import spring.sample.common.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
public class MybatisMultipleDatasourceController {

  final ModelMapper modelMapper;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistDao")
  private MybatisMultipleDatasourceDao dao;
  
  @Autowired(required = false)
  private MybatisMultipleDatasourceDao primaryDao;

  @Autowired(required = false)
  @Qualifier(value = "scientistH2Dao")
  private MybatisMultipleDatasourceDao h2Dao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistMariaDao")
  private MybatisMultipleDatasourceDao mariaDao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistOracleDao")
  private MybatisMultipleDatasourceDao oracleDao;
  
  @Autowired(required = false)
  private MybatisMultipleDatasourceVerticaDao verticaDao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistPostgresDao")
  private MybatisMultipleDatasourceDao postgresDao;
  
  @GetMapping("/v1/mybatis-multiple-datasource/all")
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> all() {
    MybatisMultipleDatasourceResDto.ScientistList resDto = new MybatisMultipleDatasourceResDto.ScientistList();
    List<MybatisMultipleDatasourceResDto.Scientist> listAll = new ArrayList<MybatisMultipleDatasourceResDto.Scientist>();
    listAll.addAll(h2Dao.findAll().stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(mariaDao.findAll().stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(oracleDao.findAll().stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(verticaDao.findAll().stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(postgresDao.findAll().stream()
        .map(item -> modelMapper.map(item, MybatisMultipleDatasourceResDto.Scientist.class))
        .collect(Collectors.toList()));
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
  
  @GetMapping("/v1/mybatis-multiple-datasource/primary")
  public ResponseDto<MybatisMultipleDatasourceResDto.ScientistList> primary() {
    List<ScientistVo> result = primaryDao.findAll();
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
