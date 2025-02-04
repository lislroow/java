package spring.custom.api.dto;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import spring.custom.common.vo.AuditVo;

public class JpaSampleDto {
  
  private JpaSampleDto() { }
  
  // planet
  @Data
  public static class Planet extends AuditVo {
    private Boolean deleted;
    
    private Integer id;
    private String name;
    private Double radius;
    private Double mass;
    private BigInteger distanceFromSun;
    private Double orbitalEccentricity;
  }
  
  @Data
  public static class PlanetRes extends Planet {
    private List<JpaSampleDto.Satellite> satellites = new ArrayList<>();
  }
  
  @Data
  public static class AddPlanetReq {
    private String name;
    private Double radius;
    private Double mass;
    private BigInteger distanceFromSun;
    private Double orbitalEccentricity;
  }
  
  @Data
  public static class ModifyPlanetReq {
    private Boolean deleted;
    
    private Integer id;
    private String name;
    private Double radius;
    private Double mass;
    private BigInteger distanceFromSun;
    private Double orbitalEccentricity;
  }
  
  
  
  // satellite
  @Data
  public static class Satellite extends AuditVo {
    private Boolean deleted;
    
    private Integer id;
    private String name;
    private Double radius;
    private Double mass;
    private BigInteger distanceFromPlanet;
    private Double orbitalEccentricity;
  }
  
  @Data
  public static class SatelliteRes extends Satellite {
    private Planet planet;
  }
  
  @Data
  public static class AddSatelliteReq {
    private String name;
    private Double radius;
    private Double mass;
    private BigInteger distanceFromPlanet;
    private Double orbitalEccentricity;
  }
  
  @Data
  public static class ModifySatelliteReq {
    private Boolean deleted;
    
    private Integer id;
    private String name;
    private Double radius;
    private Double mass;
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
