package dev.hireben.demo.rest.resource.utility.annotation;

import org.springframework.core.MethodParameter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import dev.hireben.demo.rest.resource.application.dto.UserDTO;
import dev.hireben.demo.rest.resource.presentation.model.HttpHeaderKey;

@Component
public class UserInfoResolver implements HandlerMethodArgumentResolver {

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  public boolean supportsParameter(@NonNull MethodParameter parameter) {
    return parameter.getParameterType().equals(UserDTO.class) &&
        parameter.hasParameterAnnotation(UserInfo.class);
  }

  // ---------------------------------------------------------------------------//

  @Override
  @Nullable
  public Object resolveArgument(
      @NonNull MethodParameter parameter,
      @Nullable ModelAndViewContainer mavContainer,
      @NonNull NativeWebRequest webRequest,
      @Nullable WebDataBinderFactory binderFactory) throws Exception {

    String userId = webRequest.getHeader(HttpHeaderKey.USER_ID);
    if (userId == null) {
      throw new MissingRequestHeaderException(HttpHeaderKey.USER_ID, parameter);
    }

    String tenant = webRequest.getHeader(HttpHeaderKey.USER_TENANT);
    if (tenant == null) {
      throw new MissingRequestHeaderException(HttpHeaderKey.USER_TENANT, parameter);
    }

    return UserDTO.builder()
        .id(userId)
        .tenant(tenant)
        .build();
  }

}
