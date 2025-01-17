package spring.auth.api.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.UserMngVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface UserMngDao {
  
  List<UserMngVo> allManagers();
  
  UserMngVo findManagerById(Integer id);
  
  PageResponse<UserMngVo> findManagers(PageRequest pageRequest);
  
  PageResponse<UserMngVo> searchManagers(
      PageRequest pageRequest, UserMngVo.SearchVo searchVo);
  
  int addManager(UserMngVo.AddVo addVo);
  
  int modifyManagerById(UserMngVo.ModifyVo modifyVo);
  
  int removeManagerById(Integer id);
  
}
