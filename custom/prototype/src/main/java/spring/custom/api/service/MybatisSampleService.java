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
  public int add(ScientistVo.AddVo vo) {
    vo.setCreateId(1);
    vo.setModifyId(1);
    return mybatisSampleDao.add(vo);
  }
  
  @Transactional
  public int modifyNameById(ScientistVo.ModifyVo vo) {
    vo.setModifyId(1);
    
    return mybatisSampleDao.modifyNameById(vo);
  }
  
  @Transactional
  public int removeById(ScientistVo.RemoveVo vo) {
    return mybatisSampleDao.removeById(vo);
  }
  
}
