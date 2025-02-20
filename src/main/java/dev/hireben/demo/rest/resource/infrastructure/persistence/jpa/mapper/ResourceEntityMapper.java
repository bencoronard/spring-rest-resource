package dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.mapper;

import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.entity.ResourceEntity;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceEntityMapper {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public ResourceEntity fromDomain(Resource resource) {
    return ResourceEntity.builder()
        .id(resource.getId())
        .field1(resource.getField1())
        .field2(resource.getField2())
        .field3(resource.getField3())
        .tenant(resource.getTenant())
        .createdBy(resource.getCreatedBy())
        .createdAt(resource.getCreatedAt())
        .build();
  }

  // ---------------------------------------------------------------------------//

  public Resource toDomain(ResourceEntity entity) {
    return Resource.builder()
        .id(entity.getId())
        .field1(entity.getField1())
        .field2(entity.getField2())
        .field3(entity.getField3())
        .tenant(entity.getTenant())
        .createdBy(entity.getCreatedBy())
        .createdAt(entity.getCreatedAt())
        .build();
  }

}
