package spring.auth.api.dao;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import spring.auth.api.vo.UserMngVo;
import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PageResponse;

@Mapper
public interface UserMngDao {
  
  String selectNextId(Integer idprefix);
  
  List<UserMngVo> allManagers();
  
  Optional<UserMngVo> findManagerById(String id);
  Optional<UserMngVo> findManagerByLoginId(String loginId);
  
  PageResponse<UserMngVo> findManagers(PageRequest pageRequest);
  
  PageResponse<UserMngVo> searchManagers(
      PageRequest pageRequest, UserMngVo.SearchVo searchVo);
  
  int addManager(UserMngVo.AddVo addVo);
  
  int modifyManagerById(UserMngVo.ModifyVo modifyVo);
  
  int removeManagerById(String id);
  
  Optional<String> findManagerLoginPwdById(String id);
  
  int changeManagerLoginPwdById(String id, String newLoginPwd);
  
}
