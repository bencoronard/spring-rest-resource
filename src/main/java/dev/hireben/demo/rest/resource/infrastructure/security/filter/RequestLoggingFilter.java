package dev.hireben.demo.rest.resource.infrastructure.security.filter;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.CommonsRequestLoggingFilter;

import dev.hireben.demo.rest.resource.presentation.model.HttpHeaderKey;
import dev.hireben.demo.rest.resource.presentation.model.RequestAttributeKey;
import dev.hireben.demo.rest.resource.utility.LogFormatUtil;
import dev.hireben.demo.rest.resource.utility.RequestUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RequestLoggingFilter extends CommonsRequestLoggingFilter {

  // ---------------------------------------------------------------------------//
  // Constructors
  // ---------------------------------------------------------------------------//

  public RequestLoggingFilter() {
    setIncludeQueryString(true);
    setIncludeHeaders(false);
    setIncludePayload(false);
    setMaxPayloadLength(500);
  }

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  protected void beforeRequest(@NonNull HttpServletRequest request, @NonNull String message) {

    String traceId = RequestUtil.extractHeader(request, HttpHeaderKey.TRACE_ID).orElse(request.getRequestId());

    request.setAttribute(RequestAttributeKey.TRACE_ID, traceId);

    log.info(LogFormatUtil.formatTraceLog(traceId, message));
  }

}
