package spring.sample.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.custom.common.mybatis.PageRequest;
import spring.custom.common.mybatis.PagedList;
import spring.sample.app.dao.MyTopicDao;
import spring.sample.app.vo.EmployVo;

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
  
  public PagedList<EmployVo> selectList(PageRequest param) {
    return myTopicDao.selectList(param);
  }
}
