package th.co.loxbit.rest_task_scheduler.gateway.dtos.http.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CloseGatewayResponse {

  private final String respCode;
  private final String desc;

}
