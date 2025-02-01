package spring.custom.api.controller;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import spring.custom.api.dto.JpaSampleDto;
import spring.custom.api.entity.PlanetEntity;
import spring.custom.api.entity.SatelliteEntity;
import spring.custom.api.entity.StarEntity;
import spring.custom.api.entity.repository.PlanetRepository;
import spring.custom.api.entity.repository.SatelliteRepository;
import spring.custom.api.entity.repository.StarRepository;
import spring.custom.api.entity.spec.PlanetSpecification;
import spring.custom.api.entity.spec.SatelliteSpecification;
import spring.custom.api.entity.spec.StarSpecification;
import spring.custom.api.service.JpaSampleService;
import spring.custom.common.exception.data.DataNotFoundException;

@RestController
@RequiredArgsConstructor
public class JpaSampleController {

  final ModelMapper modelMapper;
  final JpaSampleService jpaSampleService;
  @Nullable final StarRepository starRepository;
  @Nullable final SatelliteRepository satelliteRepository;
  @Nullable final PlanetRepository planetRepository;
  
  
  // planet
  @GetMapping("/v1/jpa-sample/planets/all")
  public List<JpaSampleDto.PlanetRes> allPlanets() {
    List<PlanetEntity> result = planetRepository.findAll();
    return result.stream()
        .map(planet -> {
          JpaSampleDto.PlanetRes res = modelMapper.map(planet, JpaSampleDto.PlanetRes.class);
          if (planet.getSatellites() != null) {
            List<JpaSampleDto.Satellite> satellites = planet.getSatellites()
                .stream()
                .map(satellite -> modelMapper.map(satellite, JpaSampleDto.Satellite.class))
                .toList();
            res.setSatellites(satellites);
          }
          return res;
        })
        .collect(Collectors.toList());
  }
  
  @GetMapping("/v1/jpa-sample/planets/search")
  public Page<JpaSampleDto.PlanetRes> searchPlanets(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Specification<PlanetEntity> spec = 
        Specification.where(PlanetSpecification.hasName(name));
    Page<PlanetEntity> result = planetRepository.findAll(spec, PageRequest.of(page, size));
    return result.map(planet -> {
      JpaSampleDto.PlanetRes res = modelMapper.map(planet, JpaSampleDto.PlanetRes.class);
      if (planet.getSatellites() != null) {
        List<JpaSampleDto.Satellite> satellites = planet.getSatellites()
            .stream()
            .map(satellite -> modelMapper.map(satellite, JpaSampleDto.Satellite.class))
            .toList();
        res.setSatellites(satellites);
      }
      return res;
    });
  }
  
  @GetMapping("/v1/jpa-sample/planet/{id}")
  public ResponseEntity<JpaSampleDto.PlanetRes> findPlanetById(
      @PathVariable Integer id) {
    PlanetEntity result = planetRepository.findById(id).orElseThrow(DataNotFoundException::new);
    JpaSampleDto.PlanetRes resDto = modelMapper.map(result, JpaSampleDto.PlanetRes.class);
    if (result.getSatellites() != null) {
      List<JpaSampleDto.Satellite> satellites = result.getSatellites()
          .stream()
          .map(satellite -> modelMapper.map(satellite, JpaSampleDto.Satellite.class))
          .toList();
      resDto.setSatellites(satellites);
    }
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/jpa-sample/planet")
  public ResponseEntity<?> addPlanet(
      @RequestBody JpaSampleDto.AddPlanetReq reqDto) {
    PlanetEntity result = jpaSampleService.addPlanet(reqDto);
    JpaSampleDto.PlanetRes resDto = modelMapper.map(result, JpaSampleDto.PlanetRes.class);
    
    return ResponseEntity.status(HttpStatus.CREATED)
        .location(URI.create("/v1/jpa-sample/planet/"+resDto.getId()))
        .body(resDto);
  }
  
  @PutMapping("/v1/jpa-sample/planet")
  public ResponseEntity<?> modifyPlanetById(
      @RequestBody JpaSampleDto.ModifyPlanetReq reqDto) {
    PlanetEntity result = jpaSampleService.modifyPlanetById(reqDto);
    JpaSampleDto.PlanetRes resDto = result != null 
        ? modelMapper.map(result, JpaSampleDto.PlanetRes.class)
            : null;
    
    if (result == null) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(resDto);
    }
  }
  
  @DeleteMapping("/v1/jpa-sample/planet/{id}")
  public ResponseEntity<?> removePlanetById(
      @PathVariable Integer id) {
    jpaSampleService.removePlanetById(id);
    
    return ResponseEntity.noContent().build();
  }
  
  
  
  // satellite
  @GetMapping("/v1/jpa-sample/satellites/all")
  public List<JpaSampleDto.SatelliteRes> allSatellites() {
    List<SatelliteEntity> result = satelliteRepository.findAll();
    return result.stream()
        .map(item -> modelMapper.map(item, JpaSampleDto.SatelliteRes.class))
        .collect(Collectors.toList());
  }
  
  @GetMapping("/v1/jpa-sample/satellites/search")
  public Page<JpaSampleDto.SatelliteRes> searchSatellites(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    
    Specification<SatelliteEntity> spec = 
        Specification.where(SatelliteSpecification.hasName(name));
    Page<SatelliteEntity> result = satelliteRepository.findAll(spec, PageRequest.of(page, size));
    return result.map(item -> modelMapper.map(item, JpaSampleDto.SatelliteRes.class));
  }
  
  @GetMapping("/v1/jpa-sample/satellite/{id}")
  public ResponseEntity<JpaSampleDto.SatelliteRes> findSatelliteById(
      @PathVariable Integer id) {
    SatelliteEntity result = satelliteRepository.findById(id).orElseThrow(DataNotFoundException::new);
    JpaSampleDto.SatelliteRes resDto = modelMapper.map(result, JpaSampleDto.SatelliteRes.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/jpa-sample/satellite")
  public ResponseEntity<?> addSatellite(
      @RequestBody JpaSampleDto.AddSatelliteReq reqDto) {
    SatelliteEntity result = jpaSampleService.addSatellite(reqDto);
    JpaSampleDto.SatelliteRes resDto = modelMapper.map(result, JpaSampleDto.SatelliteRes.class);
    
    return ResponseEntity.status(HttpStatus.CREATED)
        .location(URI.create("/v1/jpa-sample/satellite/"+resDto.getId()))
        .body(resDto);
  }
  
  @PutMapping("/v1/jpa-sample/satellite")
  public ResponseEntity<?> modifySatelliteById(
      @RequestBody JpaSampleDto.ModifySatelliteReq reqDto) {
    SatelliteEntity result = jpaSampleService.modifySatelliteById(reqDto);
    JpaSampleDto.SatelliteRes resDto = result != null 
        ? modelMapper.map(result, JpaSampleDto.SatelliteRes.class)
        : null;
    
    if (result == null) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(resDto);
    }
  }
  
  @DeleteMapping("/v1/jpa-sample/satellite/{id}")
  public ResponseEntity<?> removeSatelliteById(
      @PathVariable Integer id) {
    jpaSampleService.removeSatelliteById(id);
    
    return ResponseEntity.noContent().build();
  }
  
  
  
  // star
  @GetMapping("/v1/jpa-sample/stars/all")
  public List<JpaSampleDto.StarRes> allStars() {
    List<StarEntity> result = starRepository.findAll();
    return result.stream()
        .map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class))
        .collect(Collectors.toList());
  }
  
  @GetMapping("/v1/jpa-sample/stars")
  public Page<JpaSampleDto.StarRes> findStars(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    //PageResponse<StarVo.ResultStar> resultVo = jpaSampleDao.findStars(PageRequest.of(page, size));
    //PageResponse<JpaSampleDto.StarRes> resDto = new PageResponse<JpaSampleDto.StarRes>(
    //    resultVo.stream()
    //      .map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class))
    //      .collect(Collectors.toList())
    //    , resultVo.getPageInfo());
    
    Page<StarEntity> result = starRepository.findAll(PageRequest.of(page, size));
    return result.map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class));
  }
  
  @GetMapping("/v1/jpa-sample/stars/search")
  public Page<JpaSampleDto.StarRes> searchStars(
      @RequestParam(required = false) String name,
      //@RequestParam(required = false) Double distance,
      //@RequestParam(required = false) Boolean distanceGreater,
      //@RequestParam(required = false) Integer temperature,
      //@RequestParam(required = false) Boolean temperatureGreater,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    //StarVo.SearchParam searchVo = StarVo.SearchParam.builder()
    //    .name(name)
    //    .fosCd(fosCd)
    //    .build();
    //PageResponse<StarVo.ResultStar> resultVo = jpaSampleDao.searchStars(PageRequest.of(page, size), searchVo);
    //PageResponse<JpaSampleDto.StarRes> resDto = new PageResponse<JpaSampleDto.StarRes>(
    //    resultVo.stream()
    //      .map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class))
    //      .collect(Collectors.toList())
    //    , resultVo.getPageInfo());
    
    Specification<StarEntity> spec = 
        Specification.where(StarSpecification.hasName(name));
    Page<StarEntity> result = starRepository.findAll(spec, PageRequest.of(page, size));
    return result.map(item -> modelMapper.map(item, JpaSampleDto.StarRes.class));
  }
  
  @GetMapping("/v1/jpa-sample/star/{id}")
  public ResponseEntity<JpaSampleDto.StarRes> findStarById(
      @PathVariable Integer id) {
    StarEntity result = starRepository.findById(id).orElseThrow(DataNotFoundException::new);
    JpaSampleDto.StarRes resDto = modelMapper.map(result, JpaSampleDto.StarRes.class);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/jpa-sample/star")
  public ResponseEntity<?> addStar(
      @RequestBody JpaSampleDto.AddStarReq reqDto) {
    //StarVo.AddStar addVo = modelMapper.map(reqDto, StarVo.AddStar.class);
    //jpaSampleService.addStar(addVo);
    
    StarEntity result = jpaSampleService.addStar(reqDto);
    // location
    JpaSampleDto.StarRes resDto = modelMapper.map(result, JpaSampleDto.StarRes.class);
    
    return ResponseEntity.status(HttpStatus.CREATED)
        .location(URI.create("/v1/jpa-sample/star/"+resDto.getId()))
        .body(resDto);
  }
  
  @PutMapping("/v1/jpa-sample/star")
  public ResponseEntity<?> modifyStarById(
      @RequestBody JpaSampleDto.ModifyStarReq reqDto) {
    //StarVo.ModifyStar modifyVo = modelMapper.map(reqDto, StarVo.ModifyStar.class);
    //int cnt = jpaSampleService.modifyStarById(modifyVo);
    
    StarEntity result = jpaSampleService.modifyStarById(reqDto);
    JpaSampleDto.StarRes resDto = result != null 
        ? modelMapper.map(result, JpaSampleDto.StarRes.class)
        : null;
    
    if (result == null) {
      return ResponseEntity.noContent().build();
    } else {
      return ResponseEntity.ok(resDto);
    }
  }
  
  @DeleteMapping("/v1/jpa-sample/star/{id}")
  public ResponseEntity<?> removeStarById(
      @PathVariable Integer id) {
    //jpaSampleService.removeStarById(id);
    
    jpaSampleService.removeStarById(id);
    
    return ResponseEntity.noContent().build();
  }
  
}
