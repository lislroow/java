package spring.sample.mybatis.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import spring.sample.mybatis.config.mybatis.Pageable;
import spring.sample.mybatis.config.mybatis.PagedList;
import spring.sample.mybatis.dto.EmployDTO;
import spring.sample.mybatis.vo.EmployVO;

@Mapper
public interface EmployDAO {
  
  List<EmployVO> selectAll();
  EmployVO selectById(@Param("id") String id);
  int insert(EmployDTO.InsertVO param);
  PagedList<EmployVO> selectList(Pageable param);
  PagedList<EmployVO> selectListByName(EmployDTO.SelectVO param);
  int updateNameById(EmployDTO.UpdateVO param);
  int deleteById(EmployDTO.UpdateVO param);
  
}
