package th.co.loxbit.rest_task_scheduler.common.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import th.co.loxbit.rest_task_scheduler.common.http.dtos.responses.GlobalResponseBody;
import th.co.loxbit.rest_task_scheduler.common.http.utilities.ResponseBodyUtils;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<GlobalResponseBody<String>> handleCatchAllException(RuntimeException exception) {
    return new ResponseEntity<>(ResponseBodyUtils.createErrorResponseBody(exception), HttpStatus.OK);
  }

}
