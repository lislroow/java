package spring.sample.mybatis.api.datasource;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.sample.mybatis.api.employ.dto.EmployPageREQ;
import spring.sample.mybatis.api.employ.dto.EmployVO;
import spring.sample.mybatis.config.mybatis.Pageable;
import spring.sample.mybatis.config.mybatis.PagedList;

@Mapper
public interface MssqlMapper {
  
  List<EmployVO> selectAll();
  PagedList<EmployVO> selectList(Pageable param);
  PagedList<EmployVO> selectListByName(EmployPageREQ param);
  int insert(EmployVO param);
  EmployVO select(String id);
  int update(EmployVO param);
  int delete(EmployVO param);
  
}
