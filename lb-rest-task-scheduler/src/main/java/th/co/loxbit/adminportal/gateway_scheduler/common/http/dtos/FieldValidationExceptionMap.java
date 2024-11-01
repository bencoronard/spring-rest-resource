package th.co.loxbit.adminportal.gateway_scheduler.common.http.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FieldValidationExceptionMap {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String field;
  private final String message;

}
