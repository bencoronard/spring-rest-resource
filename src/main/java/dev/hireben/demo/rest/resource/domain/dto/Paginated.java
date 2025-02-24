package dev.hireben.demo.rest.resource.domain.dto;

import java.util.Collection;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Paginated<T> {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  Collection<T> content;
  int pageNumber;
  int pageSize;
  long totalElements;

}
