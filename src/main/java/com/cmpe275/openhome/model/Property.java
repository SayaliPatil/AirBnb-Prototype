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
    
    @Column(nullable=false)
	private String address;
	
	private String description;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    
    @Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    
    private int price;
    
    private int beds;
    
    private int sqft;
    
    private String wifi;
    
    private String sharingtype;
    
    private String proptype;
    
    private String headline;
    
    private int minprice;
    
    private int maxprice;
   
    private String images;
    
    private String host_email;
    
    private boolean booked_flag;
}