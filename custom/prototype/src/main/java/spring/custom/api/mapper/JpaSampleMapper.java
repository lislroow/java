package spring.custom.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.data.domain.Page;

import spring.custom.api.dto.JpaSampleDto;
import spring.custom.api.entity.PlanetEntity;
import spring.custom.api.entity.SatelliteEntity;
import spring.custom.api.entity.StarEntity;

public class JpaSampleMapper {
  
  // planet
  @Mapper(componentModel = "spring")
  public static interface PlanetMapper {
    
    JpaSampleDto.PlanetRes toDto(PlanetEntity entity);
    
    default Page<JpaSampleDto.PlanetRes> toDtoPage(Page<PlanetEntity> entities) {
      return entities.map(this::toDto);
    }
    
    default List<JpaSampleDto.PlanetRes> toDtoList(List<PlanetEntity> entities) {
      return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    PlanetEntity toEntityForAdd(JpaSampleDto.AddPlanetReq addDto);
    
    @Mappings({
      @Mapping(target = "createId", ignore = true),
      @Mapping(target = "createTime", ignore = true),
      @Mapping(target = "modifyId", ignore = true),
      @Mapping(target = "modifyTime", ignore = true),
      @Mapping(target = "deleted", ignore = true)
    })
    @Mapping(target = "satellites", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PlanetEntity updateEntityFromDto(JpaSampleDto.ModifyPlanetReq modifyDto, @MappingTarget PlanetEntity entity);
  }
  
  
  // satellite
  @Mapper(componentModel = "spring")
  public static interface SatelliteMapper {
    
    JpaSampleDto.SatelliteRes toDto(SatelliteEntity entity);
    
    default Page<JpaSampleDto.SatelliteRes> toDtoPage(Page<SatelliteEntity> entities) {
      return entities.map(this::toDto);
    }
    
    default List<JpaSampleDto.SatelliteRes> toDtoList(List<SatelliteEntity> entities) {
      return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "planet", ignore = true)
    SatelliteEntity toEntityForAdd(JpaSampleDto.AddSatelliteReq addDto);
    
    @Mappings({
      @Mapping(target = "createId", ignore = true),
      @Mapping(target = "createTime", ignore = true),
      @Mapping(target = "modifyId", ignore = true),
      @Mapping(target = "modifyTime", ignore = true),
      @Mapping(target = "deleted", ignore = true)
    })
    @Mapping(target = "planet", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SatelliteEntity updateEntityFromDto(JpaSampleDto.ModifySatelliteReq modifyDto, @MappingTarget SatelliteEntity entity);
  }
  
  
  // star
  @Mapper(componentModel = "spring")
  public static interface StarMapper {
    
    JpaSampleDto.StarRes toDto(StarEntity entity);
    
    default Page<JpaSampleDto.StarRes> toDtoPage(Page<StarEntity> entities) {
      return entities.map(this::toDto);
    }
    
    default List<JpaSampleDto.StarRes> toDtoList(List<StarEntity> entities) {
      return entities.stream().map(this::toDto).collect(Collectors.toList());
    }
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    StarEntity toEntityForAdd(JpaSampleDto.AddStarReq addDto);
    
    @Mappings({
      @Mapping(target = "createId", ignore = true),
      @Mapping(target = "createTime", ignore = true),
      @Mapping(target = "modifyId", ignore = true),
      @Mapping(target = "modifyTime", ignore = true),
      @Mapping(target = "deleted", ignore = true)
    })
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    StarEntity updateEntityFromDto(JpaSampleDto.ModifyStarReq modifyDto, @MappingTarget StarEntity entity);
  }

}
