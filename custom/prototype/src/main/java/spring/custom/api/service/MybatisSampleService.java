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
    return mybatisSampleDao.addScientist(addVo);
  }
  
  @Transactional
  public int modifyScientistById(ScientistVo.ModifyVo modifyVo) {
    return mybatisSampleDao.modifyScientistById(modifyVo);
  }
  
  @Transactional
  public int removeScientistById(Integer id) {
    return mybatisSampleDao.removeScientistById(id);
  }
  
}
