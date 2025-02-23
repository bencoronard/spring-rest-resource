package dev.hireben.demo.rest.resource.presentation.configuration;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import dev.hireben.demo.rest.resource.utility.annotation.PaginationResolver;
import dev.hireben.demo.rest.resource.utility.annotation.UserInfoResolver;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final UserInfoResolver userInfoResolver;
  private final PaginationResolver paginationResolver;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public void addArgumentResolvers(@NonNull List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(userInfoResolver);
    resolvers.add(paginationResolver);
  }

}
