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
    private String host_email;
    
    @Column(nullable=false)
    private String headline;

    @Column(nullable=false)
    private String description;
    
    @Column(nullable=false)
    private String sharingtype;
    
    @Column(nullable=false)
    private String proptype;
    
    @Column(nullable=false)
    private int beds;
    
    @Column(nullable=false)
    private int bath;
    
    @Column(nullable=false)
    private int sqft;
    
    @Column(nullable=false)
	private String address;
    
    @Column(nullable=false)
   	private String parking;
    
    
    private int parkingprice;
    
    @Column(nullable=false)
    private String wifi;
    
   	private String[] extra;
	
	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date startdate;
    
    @Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date enddate;
    
    @Column(nullable=false)
    private int weekendprice;
    
    @Column(nullable=false)
    private int weekdayprice;
    
    @Column(nullable=false)
   	private String availabledays;
    
    private int price;
   
    private int minprice;
    
    private int maxprice;
   
    private String images;
    
    private boolean booked_flag;

	public Long getProperty_id() {
		return property_id;
	}

	public void setProperty_id(Long property_id) {
		this.property_id = property_id;
	}

	public String getHost_email() {
		return host_email;
	}

	public void setHost_email(String host_email) {
		this.host_email = host_email;
	}

	public String getHeadline() {
		return headline;
	}

	public void setHeadline(String headline) {
		this.headline = headline;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSharingtype() {
		return sharingtype;
	}

	public void setSharingtype(String sharingtype) {
		this.sharingtype = sharingtype;
	}

	public String getProptype() {
		return proptype;
	}

	public void setProptype(String proptype) {
		this.proptype = proptype;
	}

	public int getBeds() {
		return beds;
	}

	public void setBeds(int beds) {
		this.beds = beds;
	}

	public int getBath() {
		return bath;
	}

	public void setBath(int bath) {
		this.bath = bath;
	}

	public int getSqft() {
		return sqft;
	}

	public void setSqft(int sqft) {
		this.sqft = sqft;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public int getParkingprice() {
		return parkingprice;
	}

	public void setParkingprice(int parkingprice) {
		this.parkingprice = parkingprice;
	}

	public String getWifi() {
		return wifi;
	}

	public void setWifi(String wifi) {
		this.wifi = wifi;
	}

	public String[] getExtra() {
		return extra;
	}

	public void setExtra(String[] extra) {
		this.extra = extra;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public int getWeekendprice() {
		return weekendprice;
	}

	public void setWeekendprice(int weekendprice) {
		this.weekendprice = weekendprice;
	}

	public int getWeekdayprice() {
		return weekdayprice;
	}

	public void setWeekdayprice(int weekdayprice) {
		this.weekdayprice = weekdayprice;
	}

	public String getAvailabledays() {
		return availabledays;
	}

	public void setAvailabledays(String availabledays) {
		this.availabledays = availabledays;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMinprice() {
		return minprice;
	}

	public void setMinprice(int minprice) {
		this.minprice = minprice;
	}

	public int getMaxprice() {
		return maxprice;
	}

	public void setMaxprice(int maxprice) {
		this.maxprice = maxprice;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public boolean isBooked_flag() {
		return booked_flag;
	}

	public void setBooked_flag(boolean booked_flag) {
		this.booked_flag = booked_flag;
	}

	public Long getId() {
		return id;
	}
    
    
}