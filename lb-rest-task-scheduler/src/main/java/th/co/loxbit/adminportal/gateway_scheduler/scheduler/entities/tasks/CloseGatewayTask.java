package th.co.loxbit.adminportal.gateway_scheduler.scheduler.entities.tasks;

import java.time.Instant;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.context.annotation.Scope;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import th.co.loxbit.adminportal.gateway_scheduler.common.exceptions.WrappingException;
import th.co.loxbit.adminportal.gateway_scheduler.gateway.services.GatewayService;
import th.co.loxbit.adminportal.gateway_scheduler.scheduler.exceptions.TaskExecutionException;

@Component
@Scope("prototype")
@Setter
@RequiredArgsConstructor
public class CloseGatewayTask extends QuartzJobBean {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final GatewayService gatewayService;

  private String message;
  private Instant start;
  private Instant end;

  // ---------------------------------------------------------------------------//
  // Methods
  // ---------------------------------------------------------------------------//

  @Override
  protected void executeInternal(@NonNull JobExecutionContext context) throws JobExecutionException {

    try {
      gatewayService.close(message, start, end);
    } catch (WrappingException e) {
      throw new TaskExecutionException(e);
    }

  }

}
