package dev.hireben.demo.rest.resource.domain.dto;

import java.util.Map;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Paginable {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  int pageNumber;
  int pageSize;
  Map<String, Boolean> sortFieldsDesc;

}
