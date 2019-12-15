package com.cmpe275.openhome.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
@NonNull
@EntityListeners(AuditingEntityListener.class)
public class Filter implements Serializable{
	
	private String email;
	
	private String month;
	
	private Long id;
}
