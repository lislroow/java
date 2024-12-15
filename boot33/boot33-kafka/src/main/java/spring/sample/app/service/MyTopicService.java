package spring.sample.app.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.sample.app.dao.MyTopicDao;
import spring.sample.app.vo.MyTopicVo;
import spring.sample.common.mybatis.Pageable;
import spring.sample.common.mybatis.PagedList;

@Service
@Transactional(readOnly = true)
public class MyTopicService {
  
  private MyTopicDao myTopicDao;
  
  public MyTopicService(MyTopicDao mapper) {
    this.myTopicDao = mapper;
  }
  
  public List<MyTopicVo> selectAll() {
    List<MyTopicVo> list = null;
    list = myTopicDao.selectAll();
    return list;
  }
  
  public PagedList<MyTopicVo> selectList(Pageable param) {
    return myTopicDao.selectList(param);
  }
}
