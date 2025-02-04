package spring.custom.api.controller;

import java.util.List;

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
import spring.custom.api.mapper.JpaSampleMapper;
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
  final JpaSampleMapper.PlanetMapper planetMapper;
  final JpaSampleMapper.SatelliteMapper satelliteMapper;
  final JpaSampleMapper.StarMapper starMapper;
  
  
  // planet
  @GetMapping("/v1/jpa-sample/planets/all")
  public List<JpaSampleDto.PlanetRes> allPlanets() {
    List<PlanetEntity> result = planetRepository.findAll();
    return planetMapper.toDtoList(result);
  }
  
  @GetMapping("/v1/jpa-sample/planets/search")
  public Page<JpaSampleDto.PlanetRes> searchPlanets(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Specification<PlanetEntity> spec = 
        Specification.where(PlanetSpecification.hasName(name));
    Page<PlanetEntity> result = planetRepository.findAll(spec, PageRequest.of(page, size));
    return planetMapper.toDtoPage(result);
  }
  
  @GetMapping("/v1/jpa-sample/planet/{id}")
  public ResponseEntity<JpaSampleDto.PlanetRes> findPlanetById(
      @PathVariable Integer id) {
    PlanetEntity result = planetRepository.findById(id).orElseThrow(DataNotFoundException::new);
    JpaSampleDto.PlanetRes resDto = planetMapper.toDto(result);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/jpa-sample/planet")
  public ResponseEntity<?> addPlanet(
      @RequestBody JpaSampleDto.AddPlanetReq addDto) {
    PlanetEntity result = jpaSampleService.addPlanet(addDto);
    JpaSampleDto.PlanetRes resDto = planetMapper.toDto(result);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(resDto);
  }
  
  @PutMapping("/v1/jpa-sample/planet")
  public ResponseEntity<?> modifyPlanetById(
      @RequestBody JpaSampleDto.ModifyPlanetReq modifyDto) {
    PlanetEntity result = jpaSampleService.modifyPlanetById(modifyDto);
    JpaSampleDto.PlanetRes resDto = planetMapper.toDto(result);
    return ResponseEntity.ok(resDto);
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
    return satelliteMapper.toDtoList(result);
  }
  
  @GetMapping("/v1/jpa-sample/satellites/search")
  public Page<JpaSampleDto.SatelliteRes> searchSatellites(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Specification<SatelliteEntity> spec = 
        Specification.where(SatelliteSpecification.hasName(name));
    Page<SatelliteEntity> result = satelliteRepository.findAll(spec, PageRequest.of(page, size));
    return satelliteMapper.toDtoPage(result);
  }
  
  @GetMapping("/v1/jpa-sample/satellite/{id}")
  public ResponseEntity<JpaSampleDto.SatelliteRes> findSatelliteById(
      @PathVariable Integer id) {
    SatelliteEntity result = satelliteRepository.findById(id).orElseThrow(DataNotFoundException::new);
    JpaSampleDto.SatelliteRes resDto = satelliteMapper.toDto(result);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/jpa-sample/satellite")
  public ResponseEntity<JpaSampleDto.SatelliteRes> addSatellite(
      @RequestBody JpaSampleDto.AddSatelliteReq addDto) {
    SatelliteEntity result = jpaSampleService.addSatellite(addDto);
    JpaSampleDto.SatelliteRes resDto = satelliteMapper.toDto(result);
    return ResponseEntity.status(HttpStatus.CREATED).body(resDto);
  }
  
  @PutMapping("/v1/jpa-sample/satellite")
  public ResponseEntity<JpaSampleDto.SatelliteRes> modifySatelliteById(
      @RequestBody JpaSampleDto.ModifySatelliteReq modifyDto) {
    SatelliteEntity result = jpaSampleService.modifySatelliteById(modifyDto);
    JpaSampleDto.SatelliteRes resDto = satelliteMapper.toDto(result);
    return ResponseEntity.ok(resDto);
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
    return starMapper.toDtoList(result);
  }
  
  @GetMapping("/v1/jpa-sample/stars")
  public Page<JpaSampleDto.StarRes> findStars(
      @RequestParam(defaultValue = "0") Integer page,
      @RequestParam(defaultValue = "10") Integer size) {
    Page<StarEntity> result = starRepository.findAll(PageRequest.of(page, size));
    return starMapper.toDtoPage(result);
  }
  
  @GetMapping("/v1/jpa-sample/stars/search")
  public Page<JpaSampleDto.StarRes> searchStars(
      @RequestParam(required = false) String name,
      @RequestParam(required = false, defaultValue = "0") Integer page,
      @RequestParam(required = false, defaultValue = "10") Integer size) {
    Specification<StarEntity> spec = 
        Specification.where(StarSpecification.hasName(name));
    Page<StarEntity> result = starRepository.findAll(spec, PageRequest.of(page, size));
    return starMapper.toDtoPage(result);
  }
  
  @GetMapping("/v1/jpa-sample/star/{id}")
  public ResponseEntity<JpaSampleDto.StarRes> findStarById(
      @PathVariable Integer id) {
    StarEntity result = starRepository.findById(id).orElseThrow(DataNotFoundException::new);
    JpaSampleDto.StarRes resDto = starMapper.toDto(result);
    return ResponseEntity.ok(resDto);
  }
  
  @PostMapping("/v1/jpa-sample/star")
  public ResponseEntity<JpaSampleDto.StarRes> addStar(
      @RequestBody JpaSampleDto.AddStarReq addDto) {
    StarEntity result = jpaSampleService.addStar(addDto);
    JpaSampleDto.StarRes resDto = starMapper.toDto(result);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(resDto);
  }
  
  @PutMapping("/v1/jpa-sample/star")
  public ResponseEntity<JpaSampleDto.StarRes> modifyStarById(
      @RequestBody JpaSampleDto.ModifyStarReq modifyDto) {
    StarEntity result = jpaSampleService.modifyStarById(modifyDto);
    JpaSampleDto.StarRes resDto = starMapper.toDto(result);
    return ResponseEntity.ok(resDto);
  }
  
  @DeleteMapping("/v1/jpa-sample/star/{id}")
  public ResponseEntity<?> removeStarById(
      @PathVariable Integer id) {
    jpaSampleService.removeStarById(id);
    return ResponseEntity.noContent().build();
  }
  
}
