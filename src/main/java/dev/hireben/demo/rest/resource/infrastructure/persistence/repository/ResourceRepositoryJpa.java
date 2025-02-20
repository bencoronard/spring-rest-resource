package dev.hireben.demo.rest.resource.infrastructure.persistence.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Repository;

import dev.hireben.demo.rest.resource.domain.dto.Paginable;
import dev.hireben.demo.rest.resource.domain.dto.Paginated;
import dev.hireben.demo.rest.resource.domain.entity.Resource;
import dev.hireben.demo.rest.resource.domain.repository.ResourceRepository;
import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.entity.ResourceEntity;
import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.mapper.ResourceEntityMapper;
import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.repository.JpaResourceRepository;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class ResourceRepositoryJpa implements ResourceRepository {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final JpaResourceRepository repository;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public Resource save(Resource resource) {
    return ResourceEntityMapper.toDomain(repository.save(ResourceEntityMapper.fromDomain(resource)));
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void delete(Resource resource) {
    repository.delete(ResourceEntityMapper.fromDomain(resource));
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Paginated<Resource> findAllByTenant(String tenant, Paginable paginable) {

    List<Order> orders = paginable.getSortFieldsDesc().entrySet().stream()
        .map(entry -> entry.getValue() ? Order.desc(entry.getKey()) : Order.asc(entry.getKey()))
        .toList();

    Pageable pageable = PageRequest.of(paginable.getPageNumber(), paginable.getPageSize(), Sort.by(orders));

    Page<ResourceEntity> page = repository.findAllByTenant(tenant, pageable);

    Collection<Resource> resources = page.getContent().stream().map(ResourceEntityMapper::toDomain).toList();

    return Paginated.<Resource>builder()
        .content(resources)
        .pageNumber(page.getNumber())
        .pageSize(page.getSize())
        .totalElements(page.getTotalElements())
        .build();
  }

  // ---------------------------------------------------------------------------//

  @Override
  public Optional<Resource> findByIdAndTenant(Long id, String tenant) {
    return repository.findByIdAndTenant(id, tenant).map(ResourceEntityMapper::toDomain);
  }

}
