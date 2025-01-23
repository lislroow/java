package spring.custom.api.dto;

import java.math.BigInteger;

import lombok.Data;
import spring.custom.common.audit.AuditVo;

public class JpaSampleDto {
  
  private JpaSampleDto() { }
  
  // satellite
  @Data
  public static class SatelliteRes extends AuditVo {
    private Integer id;
    private String name;
    private Double radius;
    private Double mass;
    private String planetName;
    private BigInteger distanceFromPlanet;
    private Double orbitalEccentricity;
  }
  
  @Data
  public static class AddSatelliteReq {
    private String name;
    private Double radius;
    private Double mass;
    private String planetName;
    private BigInteger distanceFromPlanet;
    private Double orbitalEccentricity;
  }
  
  @Data
  public static class ModifySatelliteReq {
    private Integer id;
    private String name;
    private Double radius;
    private Double mass;
    private String planetName;
    private BigInteger distanceFromPlanet;
    private Double orbitalEccentricity;
  }
  
  // star
  @Data
  public static class StarRes extends AuditVo {
    private Integer id;
    private String name;
    private Double distance;
    private Double brightness;
    private Double mass;
    private Integer temperature;
  }
  
  @Data
  public static class AddStarReq {
    private String name;
    private Double distance;
    private Double brightness;
    private Double mass;
    private Integer temperature;
  }
  
  @Data
  public static class ModifyStarReq {
    private Integer id;
    private String name;
    private Double distance;
    private Double brightness;
    private Double mass;
    private Integer temperature;
  }
  
}
