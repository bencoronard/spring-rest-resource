package dev.hireben.demo.rest.resource.application.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserDTO {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  String id;
  String tenant;

}
