package spring.sample.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.sample.app.dao.MyTopicDao;
import spring.sample.app.vo.EmployVo;
import spring.component.common.mybatis.Pageable;
import spring.component.common.mybatis.PagedList;

@Service
@Transactional(readOnly = true)
public class KafkaService {
  
  private MyTopicDao myTopicDao;
  
  public KafkaService(MyTopicDao mapper) {
    this.myTopicDao = mapper;
  }
  
  public List<EmployVo> selectAll() {
    List<EmployVo> list = null;
    list = myTopicDao.selectAll();
    return list;
  }
  
  public PagedList<EmployVo> selectList(Pageable param) {
    return myTopicDao.selectList(param);
  }
}
