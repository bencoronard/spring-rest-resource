package dev.hireben.demo.rest.resource.presentation.controller;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.hireben.demo.rest.resource.application.dto.CreateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.ResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UpdateResourceDTO;
import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.presentation.dto.CreateResourceRequest;
import dev.hireben.demo.rest.resource.presentation.dto.UpdateResourceRequest;
import dev.hireben.demo.rest.resource.presentation.service.ResourceService;
import dev.hireben.demo.rest.resource.utility.annotation.Pagination;
import dev.hireben.demo.rest.resource.utility.annotation.UserInfo;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ResourceControllerV1 {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ResourceService resourceService;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @GetMapping
  public ResponseEntity<Paginated<ResourceDTO>> retrieveAllResources(
      @Pagination Paginable paginable,
      @UserInfo UserDTO user) {

    return ResponseEntity.ok(resourceService.retrieveAll(paginable, user));
  }

  // ---------------------------------------------------------------------------//

  @GetMapping("/{id}")
  public ResponseEntity<ResourceDTO> retrieveResource(
      @PathVariable Long id,
      @UserInfo UserDTO user) {

    return ResponseEntity.ok(resourceService.retrieve(id, user));
  }

  // ---------------------------------------------------------------------------//

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> removeResource(
      @PathVariable Long id,
      @UserInfo UserDTO user) {

    resourceService.remove(id, user);

    return ResponseEntity.noContent().build();
  }

  // ---------------------------------------------------------------------------//

  @PostMapping
  public ResponseEntity<Void> insertResource(
      @RequestBody @Valid CreateResourceRequest data,
      @UserInfo UserDTO user) {

    CreateResourceDTO dto = CreateResourceDTO.builder()
        .field1(data.field1())
        .field2(data.field2())
        .field3(data.field3())
        .build();

    Long id = resourceService.insert(dto, user);

    return ResponseEntity.created(URI.create(String.format("/api/v1/resources/%d", id))).build();
  }

  // ---------------------------------------------------------------------------//

  @PatchMapping("/{id}")
  public ResponseEntity<Void> updateResource(
      @PathVariable Long id,
      @RequestBody @Valid UpdateResourceRequest data,
      @UserInfo UserDTO user) {

    UpdateResourceDTO dto = UpdateResourceDTO.builder()
        .field1(data.field1())
        .field2(data.field2())
        .field3(data.field3())
        .build();

    resourceService.update(id, dto, user);

    return ResponseEntity.noContent().build();
  }

  // ---------------------------------------------------------------------------//

  @PutMapping("/{id}")
  public ResponseEntity<Void> replaceResource(
      @PathVariable Long id,
      @RequestBody @Valid CreateResourceRequest data,
      @UserInfo UserDTO user) {

    CreateResourceDTO dto = CreateResourceDTO.builder()
        .field1(data.field1())
        .field2(data.field2())
        .field3(data.field3())
        .build();

    resourceService.replace(id, dto, user);

    return ResponseEntity.noContent().build();
  }

}
