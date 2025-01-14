package spring.custom.api.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import spring.custom.api.dao.MybatisSampleDao;
import spring.custom.api.vo.ScientistVo;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MybatisSampleService {
  
  private MybatisSampleDao mybatisSampleDao;
  
  @Transactional
  public int addScientist(ScientistVo.AddVo addVo) {
    addVo.setCreateId(1);
    addVo.setModifyId(1);
    return mybatisSampleDao.addScientist(addVo);
  }
  
  @Transactional
  public int modifyScientistById(ScientistVo.ModifyVo modifyVo) {
    modifyVo.setModifyId(1);
    
    return mybatisSampleDao.modifyScientistById(modifyVo);
  }
  
  @Transactional
  public int removeScientistById(Integer id) {
    return mybatisSampleDao.removeScientistById(id);
  }
  
}
