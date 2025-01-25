package spring.custom.api.controller.internal;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import spring.custom.api.dto.JpaSampleDto;
import spring.custom.common.jpa.RestPage;
import spring.custom.config.FeignClientConfig;

@FeignClient(name = "story-api", contextId = "jpaSampleInternalController", configuration = {FeignClientConfig.class})
public interface JpaSampleInternalController {
  
  // planet
  @GetMapping("/v1/jpa-sample/planets/all")
  public List<JpaSampleDto.PlanetRes> allPlanets();
  
  @GetMapping("/v1/jpa-sample/planets/search")
  public Page<JpaSampleDto.PlanetRes> searchPlanets(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size);
  
  
  // satellites
  @GetMapping("/v1/jpa-sample/satellites/all")
  public List<JpaSampleDto.SatelliteRes> allSatellites();
  
  @GetMapping("/v1/jpa-sample/satellites/search")
  public Page<JpaSampleDto.SatelliteRes> searchSatellites(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size);
  
  
  // stars
  @GetMapping("/v1/jpa-sample/stars/all")
  public List<JpaSampleDto.StarRes> allStars();
  
  @GetMapping("/v1/jpa-sample/stars/search")
  public RestPage<JpaSampleDto.StarRes> searchStars(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size);
  
}
