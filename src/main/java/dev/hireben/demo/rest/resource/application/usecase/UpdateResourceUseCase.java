package dev.hireben.demo.rest.resource.application.usecase;

import java.util.Optional;

import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UpdateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.application.exception.ResourceNotFoundException;
import dev.hireben.demo.rest.resource.application.mapper.ResourceMapper;
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

  public ResourceDTO updateResource(Long id, UpdateResourceDTO dto, UserDTO user) {

    Resource foundResource = repository.findByIdAndTenant(id, user.getTenant())
        .orElseThrow(() -> new ResourceNotFoundException(String.format("Failed to update: resource %s not found", id)));

    Optional.ofNullable(dto.getField1()).ifPresent(foundResource::setField1);
    Optional.ofNullable(dto.getField2()).ifPresent(foundResource::setField2);
    Optional.ofNullable(dto.getField3()).ifPresent(foundResource::setField3);

    Resource updatedResource = repository.save(foundResource);

    return ResourceMapper.toDto(updatedResource);
  }

}
