package com.cmpe275.openhome.controller;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.openhome.model.JobScheduleRequest;
import com.cmpe275.openhome.model.JobScheduleResponse;
import com.cmpe275.openhome.service.CheckInOutService;

import javax.validation.Valid;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.UUID;

@RestController
public class JobSchedulerController {

    @Autowired
    private Scheduler scheduler;

    @PostMapping("/scheduleCheckInOutJob")
    public ResponseEntity<?> scheduleCheckInJob(@Valid @RequestBody JobScheduleRequest jobScheduleRequest) {
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(jobScheduleRequest.getDateTime(), jobScheduleRequest.getTimeZone());
            if(dateTime.isBefore(ZonedDateTime.now())) {
            	JobScheduleResponse jobScheduleResponse = new JobScheduleResponse(false,
                        "date time must be after current time", null);
                return ResponseEntity.badRequest().body(jobScheduleResponse);
            }
            JobDetail jobDetail = buildJobDetail(jobScheduleRequest);
            Trigger trigger = buildJobTrigger(jobDetail, dateTime);
            scheduler.scheduleJob(jobDetail, trigger);
            JobScheduleResponse jobScheduleResponse = new JobScheduleResponse(true,
                    jobDetail.getKey().getName(), "Job Scheduled Successfully!");
            return ResponseEntity.ok(jobScheduleResponse);
        } catch (SchedulerException ex) {
            JobScheduleResponse jobScheduleResponse = new JobScheduleResponse(false,
                    "Job scheduling error. Please try later!", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(jobScheduleResponse);
        }
    }
    
    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "triggers")
                .withDescription(jobDetail.getDescription())
                .startAt(Date.from(startAt.toInstant()))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }  
    
    private JobDetail buildJobDetail(JobScheduleRequest jobScheduleRequest) {
        return JobBuilder.newJob(CheckInOutService.class)
                .withIdentity(UUID.randomUUID().toString(), "jobs")
                .withDescription(jobScheduleRequest.getJobType())
                .storeDurably()
                .build();
    }
}