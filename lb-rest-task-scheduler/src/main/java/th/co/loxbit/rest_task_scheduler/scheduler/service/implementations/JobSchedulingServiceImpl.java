package th.co.loxbit.rest_task_scheduler.scheduler.service.implementations;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import th.co.loxbit.rest_task_scheduler.scheduler.entities.GatewaySchedule;
import th.co.loxbit.rest_task_scheduler.scheduler.repository.ScheduleRepository;
import th.co.loxbit.rest_task_scheduler.scheduler.service.JobSchedulingService;

@Service
@RequiredArgsConstructor
public class JobSchedulingServiceImpl implements JobSchedulingService {

  // ---------------------------------------------------------------------------//
  // Dependencies
  // ---------------------------------------------------------------------------//

  private final ScheduleRepository scheduleRepository;

  // ---------------------------------------------------------------------------//
  // Functions
  // ---------------------------------------------------------------------------//

  @Override
  public void scheduleJob(int startTime, int endTime, String message, String owner) throws SchedulerException {

    String jobId = UUID.randomUUID().toString();

    Map<String, Object> jobData = new HashMap<>();
    jobData.put("message", message);
    jobData.put("owner", owner);

    Trigger closeGatewayTrigger = TriggerBuilder.newTrigger().withIdentity("closeOneTime", jobId)
        .startAt(Date.from(Instant.ofEpochSecond(startTime)))
        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).build();

    Trigger openGatewayTrigger = TriggerBuilder.newTrigger().withIdentity("openOneTime", jobId)
        .startAt(Date.from(Instant.ofEpochSecond(endTime)))
        .withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(0)).build();

    scheduleRepository.createSchedule(jobId, jobData, closeGatewayTrigger, openGatewayTrigger);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void descheduleJob(String jobId) throws SchedulerException {
    scheduleRepository.deleteScheduleById(jobId);
  }

  // ---------------------------------------------------------------------------//

  @Override
  public List<GatewaySchedule> getScheduledJobs() throws SchedulerException {
    return scheduleRepository.findAllSchedules();
  }

  // ---------------------------------------------------------------------------//

  @Override
  public void updateJob(String jobId, int newStartTime, int newEndTime, String newMessage, String newOwner)
      throws SchedulerException {

    scheduleRepository.deleteScheduleById(jobId);
    scheduleJob(newStartTime, newEndTime, newMessage, newOwner);
  }

}
