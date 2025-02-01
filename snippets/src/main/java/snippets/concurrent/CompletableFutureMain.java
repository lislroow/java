package snippets.concurrent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CompletableFutureMain {

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class TaskVo {
    private String name;
  }
  
  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  public static class ResultVo {
    private int totCnt = 0;
    private int normalCnt = 0;
    private int failCnt = 0;
  }
  
  
  Queue<TaskVo> taskQueue = new LinkedList<>();
  
  private CompletableFutureMain init() {
    taskQueue.add(new TaskVo("Task 1"));
    taskQueue.add(new TaskVo("Task 2"));
    taskQueue.add(new TaskVo("Task 3"));
    return this;
  }
  private void start() {
    ResultVo result = new ResultVo();
    int nThread = 3;
    ThreadPoolExecutor pool = (ThreadPoolExecutor) Executors.newFixedThreadPool(nThread);
    while (true) {
      // 1) main-thread 에서 task 를 가져온다
      TaskVo taskVo = taskQueue.poll();
      
      // 1.1 task 가 더 이상 없다면
      if (taskVo == null) {
        // 배치 종료 처리
        if (pool.getActiveCount() == 0) {
          log.info("배치 작업 완료");
          break;
        }
        // main-thread 를 1초 대기
        log.info("추가 배치 작업 없음 실행중인 {}개 작업 완료 대기", pool.getActiveCount());
        try {
          Thread.sleep(1_000L);
        } catch (InterruptedException e) {
          log.error(e.getMessage());
        }
        continue;
      }
      
      // main-thread 가 result 에 lock 을 획득 후 결과 건수를 +1 증가
      synchronized (result) {
        result.setTotCnt(result.getTotCnt() + 1);
      }
      
      CompletableFuture.supplyAsync(() -> {
        TaskVo vo = taskVo;
        log.info("task name: {}", vo.getName());
        return "done";
      }, pool);
      //taskVo = taskQueue.poll(); // Local variable taskVo defined in an enclosing scope must be final or effectively final
    }
    
    pool.shutdown();
    try {
      pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
    } catch (InterruptedException e) {
      log.error(e.getMessage());
    }
    
    log.info("배치 작업 종료: {} / {} / {}", result.getTotCnt(), result.getNormalCnt(), result.getFailCnt());
  }
  
  public static void main(String[] args) {
    new CompletableFutureMain().init().start();
  }
  
}
