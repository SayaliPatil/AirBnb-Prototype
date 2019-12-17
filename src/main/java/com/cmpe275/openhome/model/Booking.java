package com.cmpe275.openhome.model;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
@NonNull
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Table(name = "booking")
public class Booking {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	

	public Long getID() {
		return ID;
	}

	private String user_email;
    
    private String host_email;
    
	private String check_in_date;
	
	private String check_out_date;
	
	@Column(name = "property_id")
	private Long propertyId;
    
    private Long property_unique_id;
    
    private int weekdayprice;
    
    private int weekendprice;
    
    private int parkingprice;
    
    private double price;
    
    private int total_nights;

    private String availabilty_start_date;
    
    private String availabilty_end_date;
    
    private int beds;
    
    private String headline;
    
    private double amount_paid;
	
	private boolean user_checked_in_flag;
	
	private boolean user_checked_out_flag;
	
	private boolean no_show;
	
	private boolean booking_cancelled;
	
	private String user_check_out_date;
	
	@Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated_at;
	
}