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
import spring.custom.api.entity.StarEntity;

@Mapper(componentModel = "spring")
public interface StarMapper {
  
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
