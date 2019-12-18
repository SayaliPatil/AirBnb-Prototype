package com.cmpe275.openhome.controller;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cmpe275.openhome.model.JobScheduleRequest;
import com.cmpe275.openhome.model.JobScheduleResponse;
import com.cmpe275.openhome.service.CheckInOutService;
import com.cmpe275.openhome.service.TimeSet;
import com.cmpe275.openhome.utils.DateUtility;

import javax.validation.Valid;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
public class JobSchedulerController {

    @Autowired
    private Scheduler scheduler;
    
    @Autowired
    private TimeSet timeSet;
    
    @PostMapping("/scheduleCheckInOutJob")
    public ResponseEntity<?> scheduleCheckInJob(@Valid @RequestBody JobScheduleRequest jobScheduleRequest) throws ParseException {
    	System.out.println("Request sent to checkin : " +jobScheduleRequest);
    	timeSet.setDate(jobScheduleRequest.getDate());
    	System.out.println("Date set : " +timeSet.getDate());
    	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
    	String parsedTime = dateFormat.format(timeSet.getDate()).split(" ")[1];
    	String checkinTime = "03:00:00";
    	String checkoutTime = "11:00:00";
    	SimpleDateFormat sdformat = new SimpleDateFormat("HH:mm:ss");
		Date checkinDatePassed = sdformat.parse(parsedTime);
		Date actualCheckinDate = sdformat.parse(checkinTime);
		Date actualCheckoutDate = sdformat.parse(checkoutTime);
		long differenceCheckin = (checkinDatePassed.getTime() - actualCheckinDate.getTime())/60000;
		long differenceCheckout = (checkinDatePassed.getTime() - actualCheckoutDate.getTime())/6000;
		System.out.println("Checkin time difference : " +differenceCheckin);
		System.out.println("Checkout time difference : " +differenceCheckout);
		if(differenceCheckin < 0 && differenceCheckout < 0) {
			return ResponseEntity.ok("Time advancement won't have affect on check in and check out");
		}
        try {
            ZonedDateTime dateTime = ZonedDateTime.of(LocalDateTime.now().plusSeconds(10), jobScheduleRequest.getTimeZone());
            System.out.println("ZonedDateTime : " +dateTime);
            if(dateTime.isBefore(ZonedDateTime.now())) {
            	JobScheduleResponse jobScheduleResponse = new JobScheduleResponse(false,
                        "date time must be after current time", null);
            	System.out.println("DATE IS BEFORE NOW");
                return ResponseEntity.badRequest().body(jobScheduleResponse);
            }
            JobDetail jobDetail = buildJobDetail(jobScheduleRequest);
            if(differenceCheckin > 0 && differenceCheckout < 0) {
    			jobScheduleRequest.setJobType("check-in");
    			jobDetail = buildJobDetail(jobScheduleRequest);
                Trigger trigger = buildJobTrigger(jobDetail, dateTime);
                System.out.println("Trigger time start date: " +trigger.getStartTime());
                System.out.println("Trigger time fire date: " +trigger.getFinalFireTime());
                scheduler.scheduleJob(jobDetail, trigger);
    		}
            
            if(differenceCheckin > 0 && differenceCheckout > 0) {
            	jobScheduleRequest.setJobType("check-in");
    			jobDetail = buildJobDetail(jobScheduleRequest);
                Trigger trigger = buildJobTrigger(jobDetail, dateTime);
                scheduler.scheduleJob(jobDetail, trigger);
    			jobScheduleRequest.setJobType("check-out");
    			JobDetail jobDetail1 = buildJobDetail(jobScheduleRequest);
    			ZonedDateTime dateTime1 = ZonedDateTime.of(LocalDateTime.now().plusSeconds(10), jobScheduleRequest.getTimeZone());
    			System.out.println("ZonedDateTime : " +dateTime1);
    			Trigger trigger1 = buildJobTrigger(jobDetail1, dateTime1);
                scheduler.scheduleJob(jobDetail1, trigger1);
    		}
            JobScheduleResponse jobScheduleResponse = new JobScheduleResponse(true,
                    jobDetail.getKey().getName(), "Job Scheduled Successfully!");
            return ResponseEntity.ok(jobScheduleResponse);
        } catch (SchedulerException ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
        }
//		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
    }
    
    private Trigger buildJobTrigger(JobDetail jobDetail, ZonedDateTime startAt) {
        return TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(jobDetail.getKey().getName(), "triggers")
                .withDescription(jobDetail.getDescription())
                .startAt(Date.from(startAt.toInstant().minusSeconds(28800)))
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();
    }  
    
    private JobDetail buildJobDetail(JobScheduleRequest jobScheduleRequest) {
		return JobBuilder.newJob(CheckInOutService.class)
                .withIdentity(UUID.randomUUID().toString(), "jobs")
                .withDescription(jobScheduleRequest.getJobType())
                .storeDurably()
                .usingJobData("startdate", jobScheduleRequest.getSetDate())
                .build();
    }
}