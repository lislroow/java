package spring.sample.mybatis.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import spring.sample.mybatis.dao.EmployDAO;
import spring.sample.mybatis.dto.EmployDTO;
import spring.sample.mybatis.util.Uuid;
import spring.sample.mybatis.vo.EmployVO;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class EmployService {
  
  private EmployDAO employDAO;
  
  @Transactional
  public EmployVO insert(EmployDTO.InsertVO param) {
    String id = Uuid.create();
    param.setId(id);
    param.setCreateId(id); // 로그인 사용자의 id 로 해야합니다.
    param.setModifyId(id); // 로그인 사용자의 id 로 해야합니다.
    employDAO.insert(param);
    
    // insert 된 항목 return
    EmployVO result = employDAO.selectById(param.getId());
    
    // transaction 테스트를 위해 예외 발생
    // int i = 1 / 0;  // divide by 0 오류 발생으로 rollback 이 되어야 함
    return result;
  }
  
  @Transactional
  public EmployVO updateNameById(String id, String name) throws Exception {
    EmployDTO.UpdateVO param = EmployDTO.UpdateVO.builder()
        .id(id)
        .name(name)
        .build();
    
    int cnt = employDAO.updateNameById(param);
    if (cnt == 0) {
      throw new Exception("삭제 대상이 없습니다.");
    }
    EmployVO result = employDAO.selectById(param.getId());
    return result;
  }
  
  @Transactional
  public void deleteById(String id) {
    EmployDTO.UpdateVO param = EmployDTO.UpdateVO.builder()
        .id(id)
        .build();
    employDAO.deleteById(param);
  }
}
