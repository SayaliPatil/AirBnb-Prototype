package com.cmpe275.openhome.model;

import javax.persistence.EntityListeners;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.time.ZoneId;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NonNull
@EntityListeners(AuditingEntityListener.class)
public class JobScheduleRequest {
	
    private LocalDateTime dateTime;

    private ZoneId timeZone;
    
    private String jobType;
	
}