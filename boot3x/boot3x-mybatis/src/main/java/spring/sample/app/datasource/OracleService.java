package spring.sample.app.datasource;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import spring.sample.app.employ.dto.EmployPageREQ;
import spring.sample.app.employ.dto.EmployVO;
import spring.sample.config.mybatis.Pageable;
import spring.sample.config.mybatis.PagedList;
import spring.sample.util.Uuid;

@Service
@Transactional(readOnly = true)
public class OracleService {
  
  private OracleMapper mapper;
  
  //public OracleService(OracleMapper mapper) {
  //  this.mapper = mapper;
  //}
  
  public OracleService(@Qualifier("sqlSessionTemplate_oracle") SqlSessionTemplate sqlSessionTemplate) {
    this.mapper = sqlSessionTemplate.getMapper(OracleMapper.class);
  }
  
  
  public List<EmployVO> selectAll() {
    List<EmployVO> list = null;
    list = mapper.selectAll();
    return list;
  }
  
  public PagedList<EmployVO> selectList(Pageable param) {
    return mapper.selectList(param);
  }
  
  public PagedList<EmployVO> selectListByName(EmployPageREQ param) {
    return mapper.selectListByName(param);
  }
  
  @Transactional
  public EmployVO insert(EmployVO param) {
    String id = Uuid.create();
    param.setId(id);
    param.setCreateId(id); // 로그인 사용자의 id 로 해야합니다.
    param.setModifyId(id); // 로그인 사용자의 id 로 해야합니다.
    int cnt = mapper.insert(param);
    // cnt == 0 인 경우는 일반적으로 sql 관련 exception 이 발생하므로
    // 항상 cnt == 1 이 됨
    
    // insert 된 항목 return
    EmployVO vo = mapper.select(param.getId());
    
    // transaction 테스트를 위해 예외 발생
    // int i = 1 / 0;  // divide by 0 오류 발생으로 rollback 이 되어야 함
    return vo;
  }
  
  @Transactional
  public EmployVO update(EmployVO param) {
    String id = param.getId();
    param.setModifyId(id); // 로그인 사용자의 id 로 해야합니다.
    int cnt = mapper.update(param);
    EmployVO vo = mapper.select(param.getId());
    return vo;
  }
  
  @Transactional
  public void delete(EmployVO param) {
    String id = param.getId();
    int cnt = mapper.delete(param);
  }
  
}
