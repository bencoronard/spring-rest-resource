package dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.entity.ResourceEntity;

public interface JpaResourceRepository extends JpaRepository<ResourceEntity, Long> {

  void deleteByIdAndTenant(Long id, String tenant);

  Page<ResourceEntity> findAllByTenant(String tenant, Pageable pageable);

  Optional<ResourceEntity> findByIdAndTenant(Long id, String tenant);

}
