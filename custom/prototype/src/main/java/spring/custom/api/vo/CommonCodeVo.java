package spring.custom.api.vo;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import spring.custom.common.audit.AuditVo;
import spring.custom.common.mybatis.PageRequest;

public class CommonCodeVo {
  
  private CommonCodeVo() {}
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  //@Builder
  public static class CodeGroupVo extends AuditVo {
    private String cdGrp;
    private String cdGrpNm;
    private String useYn;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  //@Builder : 생성자의 인자 순서대로 타입을 추론하게 되는데, cdNm 이 seq 의 Integer 타입으로 설정되어 오류가 발생함
  // org.apache.ibatis.executor.resultset.DefaultResultSetHandler.applyColumnOrderBasedConstructorAutomapping(ResultSetWrapper, List<Class<?>>, List<Object>, Constructor<?>, boolean)
  public static class CodeVo extends AuditVo {
    private String cdGrp;
    private String cdGrpNm;
    private String cd;
    private Integer seq;
    private String cdNm;
    private String useYn;
  }
  
  @Data
  @EqualsAndHashCode(callSuper=false)
  @Builder
  public static class SearchCodeGroupVo extends PageRequest {
    private String cdGrp;
    private String cdGrpNm;
    private String useYn;
  }
  
  @Data
  @Builder
  public static class SearchCodeVo {
    private String cdGrp;
    private String cdGrpNm;
    private String cd;
    private String cdNm;
    private String useYn;
  }
  
}
