package spring.sample.app.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.sample.app.dao.ScientistDao;
import spring.sample.app.dao.ScientistVerticaDao;
import spring.sample.app.dto.ScientistResDto;
import spring.sample.app.vo.ScientistVerticaVo;
import spring.sample.app.vo.ScientistVo;
import spring.sample.common.constant.Constant;
import spring.sample.common.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = Constant.APP.BASE_URI)
public class MybatisController {

  final ModelMapper modelMapper;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistDao")
  private ScientistDao scientistDao;
  
  @Autowired(required = false)
  private ScientistDao scientistPrimaryDao;

  @Autowired(required = false)
  @Qualifier(value = "scientistH2Dao")
  private ScientistDao scientistH2Dao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistMariaDao")
  private ScientistDao scientistMariaDao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistOracleDao")
  private ScientistDao scientistOracleDao;
  
  @Autowired(required = false)
  private ScientistVerticaDao scientistVerticaDao;
  
  @Autowired(required = false)
  @Qualifier(value = "scientistPostgresDao")
  private ScientistDao scientistPostgresDao;
  
  @GetMapping("/v1/mybatis/all")
  public ResponseDto<ScientistResDto.ScientistList> all() {
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    List<ScientistResDto.Scientist> listAll = new ArrayList<ScientistResDto.Scientist>();
    listAll.addAll(scientistH2Dao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(scientistMariaDao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(scientistOracleDao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(scientistVerticaDao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    listAll.addAll(scientistPostgresDao.findAll().stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList()));
    resDto.setList(listAll);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis/standard")
  public ResponseDto<ScientistResDto.ScientistList> standard() {
    List<ScientistVo> result = scientistDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis/primary")
  public ResponseDto<ScientistResDto.ScientistList> primary() {
    List<ScientistVo> result = scientistPrimaryDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis/h2")
  public ResponseDto<ScientistResDto.ScientistList> h2() {
    List<ScientistVo> result = scientistH2Dao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis/maria")
  public ResponseDto<ScientistResDto.ScientistList> maria() {
    List<ScientistVo> result = scientistMariaDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis/oracle")
  public ResponseDto<ScientistResDto.ScientistList> oracle() {
    List<ScientistVo> result = scientistOracleDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis/vertica")
  public ResponseDto<ScientistResDto.ScientistList> vertica() {
    List<ScientistVerticaVo> result = scientistVerticaDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/v1/mybatis/postgres")
  public ResponseDto<ScientistResDto.ScientistList> postgres() {
    List<ScientistVo> result = scientistPostgresDao.findAll();
    List<ScientistResDto.Scientist> list = result.stream()
        .map(item -> modelMapper.map(item, ScientistResDto.Scientist.class))
        .collect(Collectors.toList());
    ScientistResDto.ScientistList resDto = new ScientistResDto.ScientistList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
}
