package spring.sample.app.employ;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.sample.app.employ.dto.EmployPageREQ;
import spring.sample.app.employ.dto.EmployVO;
import spring.sample.config.mybatis.Pageable;
import spring.sample.config.mybatis.PagedList;

@Mapper
public interface EmployMapper {
  
  List<EmployVO> selectAll();
  PagedList<EmployVO> selectList(Pageable param);
  PagedList<EmployVO> selectListByName(EmployPageREQ param);
  int insert(EmployVO param);
  EmployVO select(String id);
  int update(EmployVO param);
  int delete(EmployVO param);
  
}