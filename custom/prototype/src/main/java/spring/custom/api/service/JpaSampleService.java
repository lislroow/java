package spring.custom.api.service;

import org.modelmapper.ModelMapper;
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

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class JpaSampleService {
  
  final ModelMapper modelMapper;
  final StarRepository starRepository;
  final SatelliteRepository satelliteRepository;
  final PlanetRepository planetRepository;
  
  
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
          //item.setMemo(reqDto.getMemo());
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
  public StarEntity addStar(JpaSampleDto.AddStarReq reqDto) {
    //return mybatisSampleDao.addStar(addVo);
    
    StarEntity entity = modelMapper.map(reqDto, StarEntity.class);
    return starRepository.save(entity);
  }
  
  @Transactional
  public StarEntity modifyStarById(JpaSampleDto.ModifyStarReq reqDto) {
    //return mybatisSampleDao.modifyStarById(modifyVo);
    return starRepository.findById(reqDto.getId())
        .map(item -> {
          item.setName(reqDto.getName());
          item.setDistance(reqDto.getDistance());
          item.setBrightness(reqDto.getBrightness());
          item.setTemperature(reqDto.getTemperature());
          item.setMass(reqDto.getMass());
          return starRepository.save(item);
        })
        .orElse(null);
  }
  
  @Transactional
  public void removeStarById(Integer id) {
    //return mybatisSampleDao.removeStarById(id);
    starRepository.deleteById(id);
  }
  
}
