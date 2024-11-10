package spring.sample.common.aspectj.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import spring.sample.common.vo.NsBlockedClientVo;

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
public interface NsBlockedClientDao {

  /**
   * IP 차단 저장
   * @param remoteAddr IP주소
   * @param blockTime 차단시각
   * @return int
   * @date 2024-08-30
   */
  int insertBlock(@Param("remoteAddr") String remoteAddr, @Param("unblockTime") Long unblockTime);
  
  /**
   * IP 차단 조회
   * @param remoteAddr IP주소
   * @param criteriaTime 기준시각
   * @return List&lt;NsBlockedClientVo&gt;
   * @date 2024-08-30
   */
  List<NsBlockedClientVo> selectBlockedListByIpAndTime(@Param("remoteAddr") String remoteAddr, @Param("currentTime") Long currentTime);
}
