package th.co.loxbit.adminportal.gateway_scheduler.common.exceptions;

import lombok.Getter;
import th.co.loxbit.adminportal.gateway_scheduler.common.dtos.ExceptionData;

@Getter
public class WrappingException extends RuntimeException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final int SERVICE_CODE;

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public WrappingException(int serviceCode, Throwable cause) {
    super(cause);
    this.SERVICE_CODE = serviceCode;
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public ExceptionData getExceptionData() {

    ExceptionData.ExceptionDataBuilder builder = ExceptionData.builder();

    Throwable cause = this.getCause();

    // CASE_1(1): a service exception wrapped in THIS wrapper
    if (cause instanceof WrappableException) {

      WrappableException wrappedException = (WrappableException) cause;

      return builder.respCode(this.SERVICE_CODE + wrappedException.getERROR_CODE())
          .respMsg(wrappedException.getMessage())
          .debugMsg(wrappedException.getDEBUG_MSG())
          .exceptionClass(wrappedException.getClass())
          .build();
    }

    // CASE_2: a wrapper wrapped in THIS wrapper
    if (cause instanceof WrappingException) {

      WrappingException wrappedWrapper = (WrappingException) cause;
      Throwable wrappedCause = wrappedWrapper.getCause();

      // CASE_2(1): a service exception wrapped in the wrapper
      if (wrappedCause instanceof WrappableException) {

        WrappableException wrappedException = (WrappableException) wrappedCause;

        return builder
            .respCode(this.SERVICE_CODE + wrappedWrapper.getSERVICE_CODE() / 10 +
                wrappedException.getERROR_CODE())
            .respMsg(wrappedException.getMessage())
            .debugMsg(wrappedException.getDEBUG_MSG())
            .exceptionClass(wrappedException.getClass())
            .build();
      }

      // CASE_2(2): a Java exception wrapped in the wrapper
      return builder.respCode(this.SERVICE_CODE + wrappedWrapper.getSERVICE_CODE()
          / 10)
          .respMsg("Unhandled exception at server side")
          .debugMsg(wrappedCause.getMessage())
          .exceptionClass(wrappedCause.getClass())
          .build();
    }

    // CASE_1(2): a Java exception wrapped in THIS wrapper
    return builder.respCode(this.SERVICE_CODE)
        .respMsg("Unhandled exception at server side")
        .debugMsg(cause.getMessage())
        .exceptionClass(cause.getClass())
        .build();
  }

}
