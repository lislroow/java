package spring.custom.api.service;

import org.modelmapper.ModelMapper;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import spring.custom.api.dto.JpaSampleDto;
import spring.custom.api.entity.PlanetEntity;
import spring.custom.api.entity.SatelliteEntity;
import spring.custom.api.entity.StarEntity;
import spring.custom.api.entity.repository.PlanetRepository;
import spring.custom.api.entity.repository.SatelliteRepository;
import spring.custom.api.entity.repository.StarRepository;
import spring.custom.api.mapper.StarMapper;
import spring.custom.common.exception.data.DataNotFoundException;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class JpaSampleService {
  
  final ModelMapper modelMapper;
  @Nullable final StarRepository starRepository;
  @Nullable final SatelliteRepository satelliteRepository;
  @Nullable final PlanetRepository planetRepository;
  final StarMapper starMapper;
  
  // planet
  @Transactional
  public PlanetEntity addPlanet(JpaSampleDto.AddPlanetReq reqDto) {
    PlanetEntity entity = modelMapper.map(reqDto, PlanetEntity.class);
    return planetRepository.save(entity);
  }
  
  @Transactional
  public PlanetEntity modifyPlanetById(JpaSampleDto.ModifyPlanetReq reqDto) {
    return planetRepository.findById(reqDto.getId())
        .map(item -> {
          item.setName(reqDto.getName());
          item.setRadius(reqDto.getRadius());
          item.setMass(reqDto.getMass());
          item.setDistanceFromSun(reqDto.getDistanceFromSun());
          item.setOrbitalEccentricity(reqDto.getOrbitalEccentricity());
          item.setDeleted(reqDto.getDeleted());
          return planetRepository.save(item);
        })
        .orElse(null);
  }
  
  @Transactional
  public void removePlanetById(Integer id) {
    planetRepository.deleteById(id);
  }
  
  
  // satellite
  @Transactional
  public SatelliteEntity addSatellite(JpaSampleDto.AddSatelliteReq reqDto) {
    SatelliteEntity entity = modelMapper.map(reqDto, SatelliteEntity.class);
    return satelliteRepository.save(entity);
  }
  
  @Transactional
  public SatelliteEntity modifySatelliteById(JpaSampleDto.ModifySatelliteReq reqDto) {
    return satelliteRepository.findById(reqDto.getId())
        .map(item -> {
          item.setName(reqDto.getName());
          item.setRadius(reqDto.getRadius());
          item.setMass(reqDto.getMass());
          item.setDistanceFromPlanet(reqDto.getDistanceFromPlanet());
          item.setOrbitalEccentricity(reqDto.getOrbitalEccentricity());
          item.setDeleted(reqDto.getDeleted());
          return satelliteRepository.save(item);
        })
        .orElse(null);
  }
  
  @Transactional
  public void removeSatelliteById(Integer id) {
    satelliteRepository.deleteById(id);
  }
  
  
  // star
  @Transactional
  public StarEntity addStar(JpaSampleDto.AddStarReq addDto) {
    StarEntity entity = starMapper.toEntityForAdd(addDto);
    return starRepository.save(entity);
  }
  
  @Transactional
  public StarEntity modifyStarById(JpaSampleDto.ModifyStarReq modifyDto) {
    StarEntity entity = starRepository.findById(modifyDto.getId())
        .orElseThrow(DataNotFoundException::new);
    starMapper.updateEntityFromDto(modifyDto, entity);
    starRepository.save(entity);
    return entity;
  }
  
  @Transactional
  public void removeStarById(Integer id) {
    starRepository.deleteById(id);
  }
  
}
