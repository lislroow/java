package spring.component.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import spring.component.app.dao.MybatisCrudDao;
import spring.component.app.vo.ScientistVo;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MybatisCrudService {
  
  private MybatisCrudDao scientistDao;
  
  @Transactional
  public void add(ScientistVo.AddVo vo) {
    scientistDao.add(vo);
  }
  
  @Transactional
  public void modifyNameById(ScientistVo.ModifyVo vo) {
    vo.setModifyId(vo.getId());
    
    int cnt = scientistDao.modifyNameById(vo);
    if (cnt == 0) {
      throw new RuntimeException("수정 대상이 없습니다.");
    }
  }
  
  @Transactional
  public void removeById(ScientistVo.RemoveVo vo) {
    int cnt = scientistDao.removeById(vo);
    if (cnt == 0) {
      throw new RuntimeException("삭제 대상이 없습니다.");
    }
  }
}
