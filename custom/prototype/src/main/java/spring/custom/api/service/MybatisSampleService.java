package spring.custom.api.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import spring.custom.api.dao.MybatisSampleDao;
import spring.custom.api.vo.ScientistVo;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class MybatisSampleService {
  
  @Nullable final MybatisSampleDao mybatisSampleDao;
  
  @Transactional
  public int addScientist(ScientistVo.AddScientist addVo) {
    return mybatisSampleDao.addScientist(addVo);
  }
  
  @Transactional
  public int modifyScientistById(ScientistVo.ModifyScientist modifyVo) {
    return mybatisSampleDao.modifyScientistById(modifyVo);
  }
  
  @Transactional
  public int removeScientistById(Integer id) {
    return mybatisSampleDao.removeScientistById(id);
  }
  
}
