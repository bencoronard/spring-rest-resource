package th.co.loxbit.rest_task_scheduler.gateway.services.implementations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientResponseException;

import th.co.loxbit.rest_task_scheduler.common.exceptions.CatchAllServiceException;
import th.co.loxbit.rest_task_scheduler.common.factories.ConfigurableObjectFactory;
import th.co.loxbit.rest_task_scheduler.common.http.configurers.RequestInterceptorConfigurer;
import th.co.loxbit.rest_task_scheduler.common.http.configurers.RestServiceConfigurer;
import th.co.loxbit.rest_task_scheduler.common.http.exceptions.Resp4xxException;
import th.co.loxbit.rest_task_scheduler.common.http.interceptors.RequestInterceptor;
import th.co.loxbit.rest_task_scheduler.common.http.services.RestService;
import th.co.loxbit.rest_task_scheduler.common.http.services.implementations.RestServiceImpl;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.requests.outbound.CloseGatewayRequestOutbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.CloseGatewayResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.GetGatewayStatusResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.dtos.responses.inbound.OpenGatewayResponseInbound;
import th.co.loxbit.rest_task_scheduler.gateway.entities.GatewayStatus;
import th.co.loxbit.rest_task_scheduler.gateway.exceptions.InvalidGatewayStatusException;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;

@Service
public class GatewayServiceImpl implements GatewayService {

  private final RestService restService;

  public GatewayServiceImpl(
      ConfigurableObjectFactory<RequestInterceptor, RequestInterceptorConfigurer> interceptorFactory,
      ConfigurableObjectFactory<RestServiceImpl, RestServiceConfigurer> restServiceFactory,
      @Value("${api.external.gateway.secret.key}") String apiKey,
      @Value("${api.external.gateway.uri}") String baseUrl) {
    RequestInterceptor interceptor = interceptorFactory.create(
        RequestInterceptorConfigurer.builder()
            .apiKey(apiKey)
            .build());
    this.restService = restServiceFactory.create(
        RestServiceConfigurer.builder()
            .baseUrl(baseUrl)
            .interceptor(interceptor)
            .build());
  }

  @Override
  public GatewayStatus getGatewayStatus() {
    try {
      GetGatewayStatusResponseInbound response = restService.get(
          "/status",
          GetGatewayStatusResponseInbound.class);
      return GatewayStatus.fromStatus(response.getDesc());
    } catch (RestClientResponseException e) {
      throw Resp4xxException.builder()
          .serviceCode(SERVICE_CODE)
          .build();
    } catch (IllegalArgumentException e) {
      throw InvalidGatewayStatusException.builder()
          .serviceCode(SERVICE_CODE)
          .build();
    } catch (RuntimeException e) {
      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .build();
    }
  }

  @Override
  public void openGateway() {
    final int SECTION_CODE = 100;
    try {
      restService.post(
          "/open",
          null,
          OpenGatewayResponseInbound.class);
    } catch (RestClientResponseException e) {
      throw Resp4xxException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();
    } catch (RuntimeException e) {
      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();
    }
  }

  @Override
  public void closeGateway(String maintenanceMsg) {
    final int SECTION_CODE = 200;
    try {
      CloseGatewayRequestOutbound requestBody = CloseGatewayRequestOutbound.builder()
          .message(maintenanceMsg)
          .build();
      restService.post(
          "/close",
          requestBody,
          CloseGatewayResponseInbound.class);
    } catch (RestClientResponseException e) {
      throw Resp4xxException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();
    } catch (RuntimeException e) {
      throw CatchAllServiceException.builder()
          .serviceCode(SERVICE_CODE)
          .sectionCode(SECTION_CODE)
          .build();
    }
  }

}
