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
  
  @Autowired
  private EmployDao employDao;

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
  
  @GetMapping("/mybatis/v1/multiple")
  public ResponseDto<EmployResDto.EmployList> findAll() {
    List<EmployVo> result1 = employH2Dao.findAll();
    List<EmployVo> result2 = employMariaDao.findAll();
    List<EmployVo> result3 = employOracleDao.findAll();
    EmployResDto.EmployList resDto = new EmployResDto.EmployList();
    List<EmployResDto.Employ> list1 = result1.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    List<EmployResDto.Employ> list2 = result2.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    List<EmployResDto.Employ> list3 = result3.stream()
        .map(item -> modelMapper.map(item, EmployResDto.Employ.class))
        .collect(Collectors.toList());
    List<EmployResDto.Employ> listAll = new ArrayList<EmployResDto.Employ>();
    listAll.addAll(list1);
    listAll.addAll(list2);
    listAll.addAll(list3);
    resDto.setList(listAll);
    return ResponseDto.body(resDto);
  }
  
  @GetMapping("/mybatis/v1/primary")
  public ResponseDto<EmployResDto.EmployList> primary() {
    List<EmployVo> result = employDao.findAll();
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
}
