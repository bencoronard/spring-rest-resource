package th.co.loxbit.rest_task_scheduler.common.security.filters;

import java.io.IOException;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

  // ---------------------------------------------------------------------------//
  // Fields
  // ---------------------------------------------------------------------------//

  private final String API_KEY;
  private static final String API_KEY_HEADER = "X-API-KEY";

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    String apiKey = request.getHeader(API_KEY_HEADER);

    if (!isValidApiKey(apiKey)) {
      response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid API key");
      return;
    }

    filterChain.doFilter(request, response);

  }

  // ---------------------------------------------------------------------------//

  private boolean isValidApiKey(String apiKey) {
    return apiKey != null && API_KEY.equals(apiKey);
  }

}
