package dev.hireben.demo.rest.resource.infrastructure.security.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.hireben.demo.rest.resource.infrastructure.security.filter.ApiKeyFilter;
import dev.hireben.demo.rest.resource.infrastructure.security.filter.RequestLoggingFilter;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FilterConfig {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Bean
  FilterRegistrationBean<RequestLoggingFilter> requestLoggingFilter() {
    FilterRegistrationBean<RequestLoggingFilter> filter = new FilterRegistrationBean<>();
    filter.setFilter(new RequestLoggingFilter());
    filter.setOrder(0);
    filter.addUrlPatterns("/*");
    return filter;
  }

  // ---------------------------------------------------------------------------//

  @Bean
  FilterRegistrationBean<ApiKeyFilter> apiKeyFilter(@Value("${info.api.internal.secret-key}") String apiKey) {
    FilterRegistrationBean<ApiKeyFilter> filter = new FilterRegistrationBean<>();
    filter.setFilter(new ApiKeyFilter(apiKey));
    filter.setOrder(1);
    filter.addUrlPatterns("/api/*");
    return filter;
  }

}
