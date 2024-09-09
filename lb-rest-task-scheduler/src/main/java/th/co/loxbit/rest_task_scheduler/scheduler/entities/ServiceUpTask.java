package th.co.loxbit.rest_task_scheduler.scheduler.entities;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.quartz.QuartzJobBean;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.gateway.services.GatewayService;

@RequiredArgsConstructor
public class ServiceUpTask extends QuartzJobBean {

  private final GatewayService gatewayService;

  @Override
  protected void executeInternal(@NonNull JobExecutionContext context) throws JobExecutionException {

    this.gatewayService.openGateway();

  }

}
