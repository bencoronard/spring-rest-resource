package dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.entity.ResourceEntity;

import dev.hireben.demo.rest.resource.infrastructure.persistence.jpa.model.ResourceEntityId;

public interface JpaResourceRepository extends JpaRepository<ResourceEntity, ResourceEntityId> {

  Page<ResourceEntity> findAllByTenant(String tenant, Pageable pageable);

}
