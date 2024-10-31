package th.co.loxbit.adminportal.gateway_scheduler.common.dtos;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ExceptionData {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final int respCode;
  private final String respMsg;
  private final String debugMsg;
  private final Class<? extends Throwable> exceptionClass;

}
