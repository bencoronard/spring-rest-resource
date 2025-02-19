package dev.hireben.demo.rest.resource.presentation.controller;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
import dev.hireben.demo.rest.resource.presentation.model.ResponseValue;
import dev.hireben.demo.rest.resource.presentation.request.CreateResourceRequest;
import dev.hireben.demo.rest.resource.presentation.request.UpdateResourceRequest;
import dev.hireben.demo.rest.resource.presentation.response.GlobalResponseBody;
import dev.hireben.demo.rest.resource.presentation.utility.annotation.UserInfo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
public class ResourceControllerV1 {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final CreateResourceUseCase createResourceUseCase;
  private final RetrieveResourceUseCase readResourceUseCase;
  private final UpdateResourceUseCase updateResourceUseCase;
  private final DeleteResourceUseCase deleteResourceUseCase;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @GetMapping
  public ResponseEntity<GlobalResponseBody<Paginated<ResourceDTO>>> fetchAllResources(
      @RequestParam(name = "page", defaultValue = "0") int pageNumber,
      @RequestParam(name = "size", defaultValue = "10") int pageSize,
      @RequestParam(name = "sort", defaultValue = "id:asc") Collection<String> sortParams,
      @UserInfo UserDTO user) {

    Map<String, Boolean> sortFields = sortParams.stream()
        .map(param -> param.split(":"))
        .filter(parts -> parts.length == 2)
        .collect(Collectors.toMap(
            parts -> parts[0],
            parts -> "desc".equalsIgnoreCase(parts[1])));

    Paginable paginable = Paginable.builder()
        .pageNumber(pageNumber)
        .pageSize(pageSize)
        .sortFieldsDesc(sortFields)
        .build();

    Paginated<ResourceDTO> payload = readResourceUseCase.findAllResources(paginable, user);

    GlobalResponseBody<Paginated<ResourceDTO>> body = GlobalResponseBody.<Paginated<ResourceDTO>>builder()
        .code(ResponseValue.RESP_CODE_SUCCESS)
        .message("Resources")
        .payload(payload)
        .build();

    return ResponseEntity.ok(body);
  }

  // ---------------------------------------------------------------------------//

  @GetMapping("/{id}")
  public ResponseEntity<GlobalResponseBody<ResourceDTO>> fetchResource(
      @PathVariable Long id,
      @UserInfo UserDTO user) {

    ResourceDTO payload = readResourceUseCase.findResource(id, user);

    GlobalResponseBody<ResourceDTO> body = GlobalResponseBody.<ResourceDTO>builder()
        .code(ResponseValue.RESP_CODE_SUCCESS)
        .message(String.format("Resource %s", id))
        .payload(payload)
        .build();

    return ResponseEntity.ok(body);
  }

  // ---------------------------------------------------------------------------//

  @DeleteMapping("/{id}")
  public ResponseEntity<GlobalResponseBody<Void>> deleteResource(
      @PathVariable Long id,
      @UserInfo UserDTO user) {

    deleteResourceUseCase.deleteResource(id, user);

    GlobalResponseBody<Void> body = GlobalResponseBody.<Void>builder()
        .code(ResponseValue.RESP_CODE_SUCCESS)
        .message(String.format("Resource %s deleted", id))
        .payload(null)
        .build();

    return ResponseEntity.ok(body);
  }

  // ---------------------------------------------------------------------------//

  @PostMapping
  public ResponseEntity<GlobalResponseBody<ResourceDTO>> createResource(
      @RequestBody @Valid CreateResourceRequest data,
      @UserInfo UserDTO user) {

    CreateResourceDTO dto = CreateResourceDTO.builder()
        .field1(data.field1())
        .field2(data.field2())
        .field3(data.field3())
        .build();

    ResourceDTO createdResource = createResourceUseCase.createResource(dto, user);

    GlobalResponseBody<ResourceDTO> body = GlobalResponseBody.<ResourceDTO>builder()
        .code(ResponseValue.RESP_CODE_SUCCESS)
        .message("Resource created")
        .payload(createdResource)
        .build();

    return ResponseEntity.ok(body);
  }

  // ---------------------------------------------------------------------------//

  @PutMapping("/{id}")
  public ResponseEntity<GlobalResponseBody<ResourceDTO>> updateResource(
      @PathVariable Long id,
      @RequestBody @Valid UpdateResourceRequest data,
      @UserInfo UserDTO user) {

    UpdateResourceDTO dto = UpdateResourceDTO.builder()
        .field1(data.field1())
        .field2(data.field2())
        .field3(data.field3())
        .build();

    ResourceDTO updatedResource = updateResourceUseCase.updateResource(id, dto, user);

    GlobalResponseBody<ResourceDTO> body = GlobalResponseBody.<ResourceDTO>builder()
        .code(ResponseValue.RESP_CODE_SUCCESS)
        .message(String.format("Resource %s updated", id))
        .payload(updatedResource)
        .build();

    return ResponseEntity.ok(body);
  }

}
