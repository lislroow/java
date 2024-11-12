package spring.sample.app.aspectj;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import spring.sample.app.dao.BlockedClientDao;
import spring.sample.app.dao.TraceApiDao;
import spring.sample.app.vo.BlockedClientVo;
import spring.sample.app.vo.TraceApiVo;

@Aspect
@Component
@Slf4j
@RequiredArgsConstructor
public class NonSecureAspectJ {

  static List<String> cardList = Arrays.asList("1234-1234-1234-1234", "5678-5678-5678-5678");
  final TraceApiDao traceApiDao;
  final BlockedClientDao blockedClientDao;
  
  @Around("@annotation(spring.sample.common.annotation.NonSecure)")
  public Object aroundPpcToken(ProceedingJoinPoint joinPoint) throws Throwable {
    long startTime = System.currentTimeMillis();
    javax.servlet.http.HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    javax.servlet.http.HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
    
    String reqUri = request.getRequestURI();
    String method = request.getMethod();
    String contentType = request.getHeader("Content-Type");
    String remoteAddr = request.getRemoteAddr();
    String clientIp = request.getHeader("CLIENT-IP");
    String agent = request.getHeader("USER-AGENT");
    String tokenId = request.getHeader("CARD-TOKEN");
    //String cardNo = request.getHeader("CARD-NO");
    
    log.info("[CARD] {}", "메시지가 수신되었습니다.");
    log.info("[CARD] Class         : {}", joinPoint.getTarget());
    log.info("[CARD] Method          : {}", method);
    log.info("[CARD] RequestURI      : {}", reqUri);
    log.info("[CARD] ContentType     : {}", contentType);
    log.info("[CARD] RemoteAddr      : {}", remoteAddr);
    
    //expireTime = LocalDateTime.now().plusSeconds(EXPIRE_SECONDS).atZone(ZoneId.systemDefault()).toEpochSecond() * 1_000L;
    int ATTEMP_ALLOW_COUNT = 5;
    long ATTEMP_SAME_INTERVAL = 3_000L;
    int BLOCKTIME_UNIT = 2;
    long MILL = 1_000L;
    long currentTime = System.currentTimeMillis();
    {
      // IP 차단 체크 > blocked 일 경우 unblockTime 증가
      {
        List<BlockedClientVo> blockList = blockedClientDao.selectBlockedListByIpAndTime(remoteAddr, currentTime);
        long blockCount = blockList.stream().count();
        if (!(blockCount > 0)) {
          long unblockTime = currentTime + ((long)Math.pow(BLOCKTIME_UNIT, blockCount) * MILL);
          blockedClientDao.insertBlock(remoteAddr, unblockTime);
          Assert.isTrue((blockCount > 0), String.format("%s is blocked. try again in %d seconds", remoteAddr, ((long)Math.pow(BLOCKTIME_UNIT, blockCount))));
        }
      }
      
      // IP 기준 최대 시도 횟수 체크 > 초과일 경우 blocked
      // case1) 같은 IP 에서 동일한 파라미터 조회
      //   - 횟수 제한: 없음 (Infinite)
      // case2) 같은 IP 에서 여러 파라미터 조회
      //   - 파라미터 종류 허용: 3
      //   - 허용 시간: 10
      //   - 유효 여부: 3
      // case3) 다른 IP 에서 동일한 파라미터 조회
      //   - IP 변경 제한: 2
      //   - 허용 시간: 30
      //   - 유효 여부: 3
      {
        long criteriaTime = currentTime + ATTEMP_SAME_INTERVAL;
        List<TraceApiVo> tokenList = traceApiDao.selectTraceListByIpAndTime(remoteAddr, criteriaTime);
        long attempCount = tokenList.stream()
            .filter(token -> {
              return remoteAddr.equals(token.getRemoteAddr()) && 
                  token.getAccessTime() > criteriaTime;
            })
            .count();
        if (!(ATTEMP_ALLOW_COUNT > attempCount)) {
          long unblockTime = currentTime + ((long)Math.pow(BLOCKTIME_UNIT, 1) * MILL);
          blockedClientDao.insertBlock(remoteAddr, unblockTime);
          Assert.isTrue((ATTEMP_ALLOW_COUNT > attempCount), String.format("%s is blocked. exceeded maximum number of attemps. try again in %d seconds", remoteAddr, ((long)Math.pow(BLOCKTIME_UNIT, 1))));
        }
      }
      
      // 토큰 생성 혹은 조회
      TraceApiVo tokenVo = null;
      if (StringUtils.isBlank(tokenId)) { // 'CARD-TOKEN' 이 없을 경우
        tokenId = UUID.randomUUID().toString();
        traceApiDao.saveTrace(tokenId, remoteAddr, currentTime);
        tokenVo = traceApiDao.selectTraceById(tokenId);
      } else { // 'CARD-TOKEN' 이 있을 경우
        tokenVo = traceApiDao.selectTraceById(tokenId);
        if (tokenVo == null) {
          tokenId = UUID.randomUUID().toString();
          traceApiDao.saveTrace(tokenId, remoteAddr, currentTime);
        }
      }
    }
    
    log.info("[CARD] {}", "============================ Parameter ==============================");
    log.info("[CARD] {}" + "====================================================================");
    
    Object result = joinPoint.proceed(joinPoint.getArgs());
    
    long timeTaken = System.currentTimeMillis() - startTime;
    log.info("[CARD] {} [{}]", "메시지가 송신되었습니다. 처리시간: ", timeTaken);
    log.info("[CARD] Result : {}", result);
    return result;
  }
}
