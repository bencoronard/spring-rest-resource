package dev.hireben.demo.rest.resource.presentation.configuration;

import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.hireben.demo.rest.resource.infrastructure.utility.EnvironmentUtil;
import dev.hireben.demo.rest.resource.presentation.exception.model.SeverityLevel;
import dev.hireben.demo.rest.resource.presentation.model.RequestAttributeKey;
import dev.hireben.demo.rest.resource.presentation.response.GlobalResponseBody;
import dev.hireben.demo.rest.resource.presentation.utility.ExceptionUtil;
import dev.hireben.demo.rest.resource.presentation.utility.RequestUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ErrorAttributesConfig extends DefaultErrorAttributes {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ObjectMapper objectMapper;
  private final EnvironmentUtil environment;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {

    Throwable error = super.getError(webRequest);
    String errorMsg = super.getMessage(webRequest, error);
    String errorClassName = error == null ? "ServletException" : error.getClass().getName();

    String respCode = RequestUtil.getAttribute(webRequest, RequestAttributeKey.ERR_RESP_CODE, String.class)
        .orElse(error == null ? "1000" : "9999");
    String respMsg = RequestUtil.getAttribute(webRequest, RequestAttributeKey.ERR_RESP_MSG, String.class)
        .orElse(error == null ? errorMsg : "Unhandled error at server side");
    Object respData = RequestUtil.getAttribute(webRequest, RequestAttributeKey.ERR_RESP_DATA, Object.class)
        .orElse(null);

    SeverityLevel severity = RequestUtil.getAttribute(webRequest, RequestAttributeKey.ERR_SEVERITY, SeverityLevel.class)
        .orElse(error == null ? SeverityLevel.LOW : SeverityLevel.HIGH);
    String traceId = RequestUtil.getAttribute(webRequest, RequestAttributeKey.TRACE_ID, String.class)
        .orElse("TRX-NULL");

    String logString = ExceptionUtil.formatTraceLog(traceId,
        ExceptionUtil.formatDebugString(errorClassName, respCode, severity, errorMsg));

    switch (severity) {
      case HIGH:
        log.error(logString);
        break;
      case MEDIUM:
        log.warn(logString);
        break;
      default:
        log.info(logString);
        break;
    }

    GlobalResponseBody<Object> errorAttributes = GlobalResponseBody.<Object>builder()
        .code(respCode)
        .message(respMsg)
        .payload(respData != null ? respData : environment.isDev() ? errorMsg : null)
        .build();

    return objectMapper.convertValue(errorAttributes, new TypeReference<Map<String, Object>>() {
    });
  }

}
