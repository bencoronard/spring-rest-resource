package dev.hireben.demo.rest.resource.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateResourceDTO {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  String field1;
  String field2;
  String field3;

}
