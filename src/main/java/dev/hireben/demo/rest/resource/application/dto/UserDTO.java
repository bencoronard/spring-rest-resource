package dev.hireben.demo.rest.resource.application.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String id;
  private final String tenant;

}
