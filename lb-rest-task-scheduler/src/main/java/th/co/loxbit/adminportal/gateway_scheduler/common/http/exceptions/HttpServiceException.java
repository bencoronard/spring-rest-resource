package th.co.loxbit.adminportal.gateway_scheduler.common.http.exceptions;

import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappableException;

public class HttpServiceException extends WrappableException {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final int ERROR_CODE = 40;
  private static final String DEBUG_MSG = "Unable to perform the requested opertaion due to an error at the proxy server";

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public HttpServiceException(String message) {
    super(message, ERROR_CODE, DEBUG_MSG);
  }

}
