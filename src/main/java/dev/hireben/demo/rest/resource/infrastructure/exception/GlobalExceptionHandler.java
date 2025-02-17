package dev.hireben.demo.rest.resource.infrastructure.exception;

import java.io.IOException;
import java.util.Collection;
import java.util.Map;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import dev.hireben.demo.rest.resource.application.exception.ResourceNotFoundException;
import dev.hireben.demo.rest.resource.infrastructure.constant.DefaultValue;
import dev.hireben.demo.rest.resource.infrastructure.constant.RequestAttributeKey;
import dev.hireben.demo.rest.resource.infrastructure.constant.SeverityLevel;
import dev.hireben.demo.rest.resource.infrastructure.dto.FieldValidationExceptionMap;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private static final Map<Class<? extends Throwable>, HttpStatus> EXCEPTION_STATUS_MAP = Map.of(
      ResourceNotFoundException.class, HttpStatus.NOT_FOUND,
      InvalidUserInfoException.class, HttpStatus.BAD_REQUEST,
      MissingRequestHeaderException.class, HttpStatus.BAD_REQUEST);

  private static final Map<Class<? extends Throwable>, String> EXCEPTION_CODE_MAP = Map.of(
      ResourceNotFoundException.class, "4004",
      InvalidUserInfoException.class, "4000",
      MissingRequestHeaderException.class, "4014");

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @ExceptionHandler({
      ResourceNotFoundException.class,
      InvalidUserInfoException.class,
      MissingRequestHeaderException.class })
  public void handleMvcException(
      RuntimeException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Class<? extends Throwable> errorClass = exception.getClass();

    String code = EXCEPTION_CODE_MAP.getOrDefault(errorClass, DefaultValue.RESP_CODE_UNKNOWN);
    String message = exception.getMessage();
    HttpStatus status = EXCEPTION_STATUS_MAP.getOrDefault(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, code);
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, message);
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(status.value(), message);
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(DataIntegrityViolationException.class)
  public void handleDataIntegrityViolationException(
      DataIntegrityViolationException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, "4009");
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, "Data integrity violation");
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.CONFLICT.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public void handleFieldValidationException(
      MethodArgumentNotValidException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Collection<ObjectError> validationErrors = exception.getBindingResult().getAllErrors();

    Collection<FieldValidationExceptionMap> validationErrorMaps = validationErrors.stream()
        .map(validationError -> FieldValidationExceptionMap.builder()
            .field(((FieldError) validationError).getField())
            .message(validationError.getDefaultMessage())
            .build())
        .toList();

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, "4010");
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, "Field validation errors");
    request.setAttribute(RequestAttributeKey.ERR_RESP_DATA, validationErrorMaps);
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(ConstraintViolationException.class)
  public void handleConstraintViolationException(ConstraintViolationException exception, HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    Collection<ConstraintViolation<?>> violationErrors = exception.getConstraintViolations();

    Collection<FieldValidationExceptionMap> violationErrorMaps = violationErrors.stream()
        .map(violationError -> FieldValidationExceptionMap.builder()
            .field(violationError.getPropertyPath().toString())
            .message(violationError.getMessage())
            .build())
        .toList();

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, "4011");
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, "Constraint violation errors");
    request.setAttribute(RequestAttributeKey.ERR_RESP_DATA, violationErrorMaps);
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public void handleHttpMessageNotReadableException(
      HttpMessageNotReadableException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, DefaultValue.RESP_CODE_UNKNOWN);
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, "Required request body is missing or unreadable");
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.BAD_REQUEST.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(NoResourceFoundException.class)
  public void handleNoResourceFoundException(
      NoResourceFoundException exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, "4404");
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG,
        String.format("Endpoint %s %s not supported", exception.getHttpMethod(), exception.getResourcePath()));
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.LOW);

    response.sendError(HttpStatus.NOT_FOUND.value(), exception.getMessage());
  }

  // ---------------------------------------------------------------------------//

  @ExceptionHandler(Exception.class)
  public void handleException(
      Exception exception,
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {

    request.setAttribute(RequestAttributeKey.ERR_RESP_CODE, DefaultValue.RESP_CODE_UNKNOWN);
    request.setAttribute(RequestAttributeKey.ERR_RESP_MSG, DefaultValue.RESP_MSG_UNKNOWN);
    request.setAttribute(RequestAttributeKey.ERR_SEVERITY, SeverityLevel.HIGH);

    response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), exception.getMessage());
  }

}
