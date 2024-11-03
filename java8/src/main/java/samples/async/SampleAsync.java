package samples.async;
import java.util.concurrent.CompletableFuture;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SampleAsync {
  
  public static void main(String[] args) {
    
    CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(1000);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        Thread.currentThread().interrupt();
      }
      return "첫 번째 작업 완료!";
    });
    
    CompletableFuture<String> future2 = CompletableFuture.supplyAsync(() -> {
      try {
        Thread.sleep(500);
      } catch (InterruptedException e) {
        log.error(e.getMessage());
        Thread.currentThread().interrupt();
      }
      return "두 번째 작업 완료!";
    });
    
    CompletableFuture<Void> combinedFuture = future1.thenCombine(future2, (result1, result2) -> {
      log.info(result1);
      log.info(result2);
      return null;
    });
    
    combinedFuture.join();  // 모든 작업이 끝날 때까지 대기
    
    log.info("최종 완료");
  }
}
