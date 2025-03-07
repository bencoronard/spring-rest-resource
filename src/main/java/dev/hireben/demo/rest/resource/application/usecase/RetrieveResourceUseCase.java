package dev.hireben.demo.rest.resource.application.usecase;

import java.util.Collection;

import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.application.exception.ResourceNotFoundException;
import dev.hireben.demo.rest.resource.application.mapper.ResourceMapper;
import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RetrieveResourceUseCase {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceRepository repository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public ResourceDTO find(Long id, UserDTO user) {

    Resource resource = repository.findByIdAndTenant(id, user.getTenant())
        .orElseThrow(() -> new ResourceNotFoundException(String.format("Resource %s not found", id)));

    return ResourceMapper.toDto(resource);
  }

  // ---------------------------------------------------------------------------//

  public Paginated<ResourceDTO> findAll(Paginable paginable, UserDTO user) {

    Paginated<Resource> page = repository.findAllByTenant(paginable, user.getTenant());

    Collection<ResourceDTO> resources = page.getContent().stream()
        .map(resource -> ResourceMapper.toDto(resource))
        .toList();

    return Paginated.<ResourceDTO>builder()
        .content(resources)
        .pageNumber(page.getPageNumber())
        .pageSize(page.getPageSize())
        .totalElements(page.getTotalElements())
        .build();
  }

}
