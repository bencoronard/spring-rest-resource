package dev.hireben.demo.rest.resource.application.usecase;

import java.time.Instant;

import dev.hireben.demo.rest.resource.application.dto.CreateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.application.mapper.ResourceMapper;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateResourceUseCase {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceRepository repository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public ResourceDTO createResource(CreateResourceDTO dto, UserDTO user) {

    Resource resource = Resource.builder()
        .field1(dto.getField1())
        .field2(dto.getField2())
        .field3(dto.getField3())
        .tenant(user.getTenant())
        .createdBy(user.getId())
        .createdAt(Instant.now())
        .build();

    Resource savedResource = repository.save(resource);

    return ResourceMapper.toDto(savedResource);
  }

}
