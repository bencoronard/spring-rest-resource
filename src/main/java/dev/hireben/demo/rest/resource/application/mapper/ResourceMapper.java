package dev.hireben.demo.rest.resource.application.mapper;

import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceMapper {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public ResourceDTO toDto(Resource entity) {
    return ResourceDTO.builder()
        .id(entity.getId())
        .field1(entity.getField1())
        .field2(entity.getField2())
        .field3(entity.getField3())
        .createdBy(entity.getCreatedBy())
        .build();
  }

}
