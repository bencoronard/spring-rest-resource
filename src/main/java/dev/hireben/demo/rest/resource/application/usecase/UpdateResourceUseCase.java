package dev.hireben.demo.rest.resource.application.usecase;

import java.time.Instant;
import java.util.Optional;

import dev.hireben.demo.rest.resource.application.dto.CreateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UpdateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.application.exception.ResourceNotFoundException;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UpdateResourceUseCase {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceRepository repository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public void update(Long id, UpdateResourceDTO dto, UserDTO user) {

    Resource resource = repository.findByIdAndTenant(id, user.getTenant())
        .orElseThrow(() -> new ResourceNotFoundException(String.format("Failed to update: resource %s not found", id)));

    Optional.ofNullable(dto.getField1()).ifPresent(resource::setField1);
    Optional.ofNullable(dto.getField2()).ifPresent(resource::setField2);
    Optional.ofNullable(dto.getField3()).ifPresent(resource::setField3);

    repository.save(resource);
  }

  // ---------------------------------------------------------------------------//

  public Long replace(Long id, CreateResourceDTO dto, UserDTO user) {

    Optional<Resource> existingResource = repository.findByIdAndTenant(id, user.getTenant());

    boolean isNewResource = existingResource.isEmpty();

    Resource newResource = existingResource.orElseGet(() -> Resource.builder()
        .field1(dto.getField1())
        .field2(dto.getField2())
        .field3(dto.getField3())
        .createdBy(user.getId())
        .tenant(user.getTenant())
        .createdAt(Instant.now())
        .build());

    newResource.setField1(dto.getField1());
    newResource.setField2(dto.getField2());
    newResource.setField3(dto.getField3());

    Long newId = repository.save(newResource);

    return isNewResource ? newId : null;
  }

}
