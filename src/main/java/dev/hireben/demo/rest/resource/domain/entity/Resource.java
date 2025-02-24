package dev.hireben.demo.rest.resource.domain.entity;

import java.time.Instant;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Resource {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final Long id;
  private final String tenant;
  private final String createdBy;
  private final Instant createdAt;
  private String field1;
  private String field2;
  private String field3;

}
