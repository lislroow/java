package spring.sample.app.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dao.QualiSrvcDao;
import spring.sample.app.vo.QualiSrvcDataVo;
import spring.sample.app.vo.QualiSrvcVo;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class QualiSrvcService {
  
  private final QualiSrvcDao dao;
  
  public void saveSrvc(QualiSrvcVo vo) {
    dao.saveSrvc(vo);
  }
  
  public void saveSrvcData(QualiSrvcDataVo vo) {
    dao.saveSrvcData(vo);
  }
}
