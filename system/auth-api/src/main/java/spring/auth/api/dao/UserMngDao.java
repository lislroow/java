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
  
  List<UserMngVo.ResultManager> allManagers();
  
  Optional<UserMngVo.ResultManager> findManagerById(String id);
  Optional<UserMngVo.ResultManager> findManagerByLoginId(String loginId);
  
  PageResponse<UserMngVo.ResultManager> findManagers(PageRequest pageRequest);
  
  PageResponse<UserMngVo.ResultManager> searchManagers(
      PageRequest pageRequest, UserMngVo.SearchParam searchVo);
  
  int addManager(UserMngVo.AddManager addVo);
  
  int modifyManagerById(UserMngVo.ModifyManager modifyVo);
  
  int removeManagerById(String id);
  
  Optional<String> findManagerLoginPwdById(String id);
  
  int changeManagerLoginPwdById(String id, String newLoginPwd);
  
}
