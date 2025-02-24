package dev.hireben.demo.rest.resource.presentation.service;

import dev.hireben.demo.rest.resource.application.dto.CreateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UpdateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;

public interface ResourceService {

  Paginated<ResourceDTO> retrieveAll(Paginable paginable, UserDTO user);

  ResourceDTO retrieve(Long id, UserDTO user);

  void remove(Long id, UserDTO user);

  Long insert(CreateResourceDTO dto, UserDTO user);

  void update(Long id, UpdateResourceDTO dto, UserDTO user);

  void replace(Long id, CreateResourceDTO dto, UserDTO user);

}
