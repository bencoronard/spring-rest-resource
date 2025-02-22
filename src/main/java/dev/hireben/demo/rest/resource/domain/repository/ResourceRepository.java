package dev.hireben.demo.rest.resource.domain.repository;

import java.util.Optional;

import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.domain.entity.Resource;

public interface ResourceRepository {

  Long save(Resource resource);

  void deleteByIdAndTenant(Long id, String tenant);

  Paginated<Resource> findAllByTenant(Paginable paginable, String tenant);

  Optional<Resource> findByIdAndTenant(Long id, String tenant);

}
