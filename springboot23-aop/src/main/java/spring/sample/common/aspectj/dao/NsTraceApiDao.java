package spring.sample.common.aspectj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import spring.sample.common.vo.NsTraceApiVo;

/**
 * 카드 비회원 인증
 *  
 * @author myeonggu.kim
 * @name
 * @date 2024-08-30
 * @description:
 */
@Mapper
@Repository
public interface NsTraceApiDao {

  /**
   * 추적정보 조회  
   * @param id uuid
   * @return NsTraceApiVo
   * @date 2024-08-30
   */
  NsTraceApiVo selectTraceById(@Param("id") String id);
  
  /**
   * 추적정보 목록 조회
   * @param remoteAddr IP주소
   * @param criteriaTime 기준시각
   * @return List&lt;NsTraceApiVo&gt;
   * @date 2024-08-30
   */
  List<NsTraceApiVo> selectTraceListByIpAndTime(@Param("remoteAddr") String remoteAddr, @Param("criteriaTime") Long criteriaTime);
  
  /**
   * 추적정보 저장
   * @param id uuid
   * @param remoteAddr IP주소
   * @param lastTrytime 미지막시도시각
   * @param failCount 실패 횟수
   * @return int
   * @date 2024-08-30
   */
  int saveTrace(@Param("id") String id, @Param("remoteAddr") String remoteAddr, @Param("currentTime") Long currentTime);
}
