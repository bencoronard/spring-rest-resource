package dev.hireben.demo.rest.resource.application.usecase;

import java.time.Instant;

import dev.hireben.demo.rest.resource.application.dto.CreateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
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

  public Long create(CreateResourceDTO dto, UserDTO user) {

    Resource resource = Resource.builder()
        .field1(dto.getField1())
        .field2(dto.getField2())
        .field3(dto.getField3())
        .createdBy(user.getId())
        .tenant(user.getTenant())
        .createdAt(Instant.now())
        .build();

    return repository.save(resource);
  }

}
