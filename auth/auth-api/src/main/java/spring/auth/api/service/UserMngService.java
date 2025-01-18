package spring.auth.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import spring.auth.api.dao.UserMngDao;
import spring.auth.api.vo.UserMngVo;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserMngService {
  
  private UserMngDao userMngDao;
  
  @Transactional
  public int sendRegisterCode(UserMngVo.AddVo addVo) {
    
    return userMngDao.addManager(addVo);
  }
  
  @Transactional
  public int modifyManagerById(UserMngVo.ModifyVo modifyVo) {
    return userMngDao.modifyManagerById(modifyVo);
  }
  
  @Transactional
  public int removeManagerById(String id) {
    return userMngDao.removeManagerById(id);
  }
  
  @Transactional
  public int changeManagerLoginPwdById(String id, String newLoginPwd) {
    return userMngDao.changeManagerLoginPwdById(id, newLoginPwd);
  }
  
}
