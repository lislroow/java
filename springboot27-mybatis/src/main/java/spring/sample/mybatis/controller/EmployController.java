package spring.sample.mybatis.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.sample.mybatis.config.mybatis.Pageable;
import spring.sample.mybatis.config.mybatis.PagedList;
import spring.sample.mybatis.dao.EmployDAO;
import spring.sample.mybatis.dto.EmployDTO;
import spring.sample.mybatis.service.EmployService;
import spring.sample.mybatis.vo.EmployVO;

@RestController
@RequiredArgsConstructor
public class EmployController {

  private final EmployService employService;
  
  private final EmployDAO employDAO;
  
  @GetMapping("/v1/mybatis/employ/all")
  public List<EmployVO> findAll() {
    List<EmployVO> result = employDAO.selectAll();
    return result;
  }
  
  @GetMapping("/v1/mybatis/employ/{id}")
  public EmployVO findById(@PathVariable String id) {
    EmployVO result = employDAO.selectById(id);
    return result;
  }
  
  // --data '{"name": "gdhong"}' --header 'Content-Type: application/json'
  @PostMapping("/v1/mybatis/employ")
  public EmployVO add(@RequestBody EmployDTO.InsertVO reqVO) {
    EmployVO result = employService.insert(reqVO);
    return result;
  }
  
  // ?page=2\&pageSize=3
  @GetMapping("/v1/mybatis/employ")
  public PagedList<EmployVO> findList(Pageable param) {
    PagedList<EmployVO> result = employDAO.selectList(param);
    return result;
  }
  
  // /v1/mybatis/employ/list/lee?page=1\&pageSize=2
  @GetMapping("/v1/mybatis/employ/list/{name}")
  public PagedList<EmployVO> findListByName(@PathVariable String name, Pageable page) {
    EmployDTO.SelectVO param = EmployDTO.SelectVO.builder()
        .name(name)
        .build();
    param.setPageable(page);
    PagedList<EmployVO> result = employDAO.selectListByName(param);
    return result;
  }
  
  @PutMapping("/v1/mybatis/employ/{id}")
  public EmployVO updateNameById(@PathVariable String id, String name) throws Exception {
    EmployVO result = employService.updateNameById(id, name);
    return result;
  }
  
  @DeleteMapping("/v1/mybatis/employ/{id}")
  public void deleteById(@PathVariable String id) {
    employService.deleteById(id);
  }
}
