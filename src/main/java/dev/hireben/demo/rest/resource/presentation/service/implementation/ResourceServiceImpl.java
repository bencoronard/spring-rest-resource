package dev.hireben.demo.rest.resource.presentation.service.implementation;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dev.hireben.demo.rest.resource.application.dto.CreateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UpdateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.application.usecase.CreateResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.DeleteResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.RetrieveResourceUseCase;
import dev.hireben.demo.rest.resource.application.usecase.UpdateResourceUseCase;
import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.presentation.service.ResourceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourceServiceImpl implements ResourceService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final CreateResourceUseCase createResourceUseCase;
  private final RetrieveResourceUseCase retrieveResourceUseCase;
  private final UpdateResourceUseCase updateResourceUseCase;
  private final DeleteResourceUseCase deleteResourceUseCase;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  @Transactional(readOnly = true)
  public Paginated<ResourceDTO> retrieveAll(Paginable paginable, UserDTO user) {
    return retrieveResourceUseCase.findAll(paginable, user);
  }

  // ---------------------------------------------------------------------------//

  @Override
  @Transactional(readOnly = true)
  public ResourceDTO retrieve(Long id, UserDTO user) {
    return retrieveResourceUseCase.find(id, user);
  }

  // ---------------------------------------------------------------------------//

  @Override
  @Transactional
  public void remove(Long id, UserDTO user) {
    deleteResourceUseCase.delete(id, user);
  }

  // ---------------------------------------------------------------------------//

  @Override
  @Transactional
  public Long insert(CreateResourceDTO dto, UserDTO user) {
    return createResourceUseCase.create(dto, user);
  }

  // ---------------------------------------------------------------------------//

  @Override
  @Transactional
  public void update(Long id, UpdateResourceDTO dto, UserDTO user) {
    updateResourceUseCase.update(id, dto, user);
  }

  // ---------------------------------------------------------------------------//

  @Override
  @Transactional
  public void replace(Long id, CreateResourceDTO dto, UserDTO user) {
    updateResourceUseCase.replace(id, dto, user);
  }

}
