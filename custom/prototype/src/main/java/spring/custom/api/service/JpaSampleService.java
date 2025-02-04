package spring.custom.api.service;

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
import spring.custom.api.mapper.JpaSampleMapper;
import spring.custom.common.exception.data.DataNotFoundException;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class JpaSampleService {
  
  @Nullable final StarRepository starRepository;
  @Nullable final SatelliteRepository satelliteRepository;
  @Nullable final PlanetRepository planetRepository;
  final JpaSampleMapper.PlanetMapper planetMapper;
  final JpaSampleMapper.SatelliteMapper satelliteMapper;
  final JpaSampleMapper.StarMapper starMapper;
  
  // planet
  @Transactional
  public PlanetEntity addPlanet(JpaSampleDto.AddPlanetReq addDto) {
    PlanetEntity entity = planetMapper.toEntityForAdd(addDto);
    planetRepository.save(entity);
    if (entity.getSatellites() != null) {
      for (SatelliteEntity satellite : entity.getSatellites()) {
        satellite.setPlanet(entity);
        satellite.setDeleted(false); // 코드 개선 필요
      }
      satelliteRepository.saveAll(entity.getSatellites());
    }
    return entity;
  }
  
  @Transactional
  public PlanetEntity modifyPlanetById(JpaSampleDto.ModifyPlanetReq modifyDto) {
    PlanetEntity entity = planetRepository.findById(modifyDto.getId()).orElseThrow(DataNotFoundException::new);
    planetMapper.updateEntityFromDto(modifyDto, entity);
    planetRepository.save(entity);
    return entity;
  }
  
  @Transactional
  public void removePlanetById(Integer id) {
    planetRepository.deleteById(id);
  }
  
  
  // satellite
  @Transactional
  public SatelliteEntity addSatellite(JpaSampleDto.AddSatelliteReq addDto) {
    SatelliteEntity entity = satelliteMapper.toEntityForAdd(addDto);
    return satelliteRepository.save(entity);
  }
  
  @Transactional
  public SatelliteEntity modifySatelliteById(JpaSampleDto.ModifySatelliteReq modifyDto) {
    SatelliteEntity entity = satelliteRepository.findById(modifyDto.getId())
        .orElseThrow(DataNotFoundException::new);
    satelliteMapper.updateEntityFromDto(modifyDto, entity);
    satelliteRepository.save(entity);
    return entity;
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
