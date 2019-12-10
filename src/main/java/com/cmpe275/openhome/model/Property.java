package com.cmpe275.openhome.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

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
@Table(name = "availableprop")
public class Property {
	
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Long id;
    
    private Long property_id;
    
    private String host_email;
    
    private String headline;

    private String description;
    
    private String sharingtype;
    
    private String proptype;
    
    private int beds;
    
    private int bath;
    
    private int sqft;
    
	private String address;
    
   	private String parking;
    
    
    private int parkingprice;
    
    private String wifi;
    
   	private String[] extra;

	@Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    
	@Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    
    private int weekendprice;
    
    private int weekdayprice;
    
   	private String availabledays;
    
    private int price;
   
    private int minprice;
    
    private int maxprice;
   
    @Column(columnDefinition = "varchar(1024)")
    private String images;
    
    private boolean booked_flag;
    
    @Column(columnDefinition = "boolean default false")
    private boolean is_deleted;
    
}