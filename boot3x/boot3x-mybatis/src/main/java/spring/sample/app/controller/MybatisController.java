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
import spring.sample.app.dao.EmployDao;
import spring.sample.app.dao.EmployVerticaDao;
import spring.sample.app.dto.EmployResDto;
import spring.sample.app.vo.EmployVerticaVo;
import spring.sample.app.vo.EmployVo;
import spring.sample.common.dto.ResponseDto;

@RestController
@RequiredArgsConstructor
public class MybatisController {

  final ModelMapper modelMapper;
  
  @Autowired(required = false)
  @Qualifier(value = "employDao")
  private EmployDao employDao;
  
  @Autowired
  private EmployDao employPrimaryDao;

  @Autowired
  @Qualifier(value = "employH2Dao")
  private EmployDao employH2Dao;
  
  @Autowired
  @Qualifier(value = "employMariaDao")
  private EmployDao employMariaDao;
  
  @Autowired
  @Qualifier(value = "employOracleDao")
  private EmployDao employOracleDao;
  
  @Autowired
  private EmployVerticaDao employVerticaDao;
  
  @Autowired
  @Qualifier(value = "employPostgresDao")
  private EmployDao employPostgresDao;
  
  @GetMapping("/mybatis/v1/all")
  public ResponseDto<EmployResDto.EmployList> all() {
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    List<EmployResDto.Employ> listAll = new ArrayList<EmployResDto.Employ>();
    listAll.addAll(employH2Dao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    listAll.addAll(employMariaDao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    listAll.addAll(employOracleDao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    listAll.addAll(employVerticaDao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    listAll.addAll(employPostgresDao.findAll().stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList()));
    resDto.setList(listAll);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/standard")
  public ResponseDto<EmployResDto.EmployList> standard() {
    List<EmployVo> result = employDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/primary")
  public ResponseDto<EmployResDto.EmployList> primary() {
    List<EmployVo> result = employPrimaryDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/h2")
  public ResponseDto<EmployResDto.EmployList> h2() {
    List<EmployVo> result = employH2Dao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/maria")
  public ResponseDto<EmployResDto.EmployList> maria() {
    List<EmployVo> result = employMariaDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/oracle")
  public ResponseDto<EmployResDto.EmployList> oracle() {
    List<EmployVo> result = employOracleDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/vertica")
  public ResponseDto<EmployResDto.EmployList> vertica() {
    List<EmployVerticaVo> result = employVerticaDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/postgres")
  public ResponseDto<EmployResDto.EmployList> postgres() {
    List<EmployVo> result = employPostgresDao.findAll();
    List<EmployResDto.Employ> list = result.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    resDto.setList(list);
    return ResponseDto.body(resDto);
  }
}
