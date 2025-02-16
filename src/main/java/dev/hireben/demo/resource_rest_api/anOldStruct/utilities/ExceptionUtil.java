package dev.hireben.demo.resource_rest_api.anOldStruct.utilities;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ExceptionUtil {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String DEBUG_STRING_FORMAT = "%s[code=%s, severity=%s, message=%s]";
  private final String DEBUG_LOG_FORMAT = "Trace: %s >>> %s";

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  public String formatDebugString(Class<? extends Throwable> clazz, String code, SeverityLevel severity,
      String message) {
    return String.format(DEBUG_STRING_FORMAT, clazz.getName(), code, severity.name(), message);
  }

  // ---------------------------------------------------------------------------//

  public String formatTraceLog(String traceId, String message) {
    return String.format(DEBUG_LOG_FORMAT, traceId, message);
  };

}
