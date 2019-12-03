package com.cmpe275.openhome.model;

import java.io.Serializable;
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

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
//import org.springframework.lang.NonNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"}, allowGetters = true)
@Table(name = "dharmatestmodel")
public class DharmaTestModel implements Serializable  {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
    private Long id;
	
	@Column(nullable=false)
	private String propname;
	
	@Column(nullable=false)
	private String propdesc;
	
	@Column(nullable=false)
	private String sharingtype;
    
	@Column(nullable=false)
    private String proptype;
    
	@Column(nullable=false)
    private int bed;
	
	@Column(nullable=false)
    private int bath;
    
	@Column(nullable=false)
    private int capacity;
    
	@Column(nullable=false)
    private int area;

    @Column(nullable=false)
	private String address;
    
    @Column(nullable=false)
   	private String address2;

    @Column(nullable=false)
   	private String city;
    
    @Column(nullable=false)
   	private String state;
    
    @Column(nullable=false)
   	private String zip;
    
    @Column(nullable=false)
   	private String parking;
    
    private String parkingprice;
    
    @Column(nullable=false)
   	private String wifi;
    
    @Column(nullable=false)
   	private String[] extra;
    
//	@Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date startDate;
    
//    @Column(nullable=false)
	@Temporal(TemporalType.TIMESTAMP)
    private Date endDate;
    
    @Column(nullable=false)
    private int weekendprice;
    
    @Column(nullable=false)
    private int weekdayprice;
    
    @Column(nullable=false)
   	private String[] availabledays;
   
    private String images;

	public String getPropname() {
		return propname;
	}

	public void setPropname(String propname) {
		this.propname = propname;
	}

	public String getPropdesc() {
		return propdesc;
	}

	public void setPropdesc(String propdesc) {
		this.propdesc = propdesc;
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

	public int getBed() {
		return bed;
	}

	public void setBed(int bed) {
		this.bed = bed;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getArea() {
		return area;
	}

	public void setArea(int area) {
		this.area = area;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getParking() {
		return parking;
	}

	public void setParking(String parking) {
		this.parking = parking;
	}

	public String getParkingprice() {
		return parkingprice;
	}

	public void setParkingprice(String parkingprice) {
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

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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

	public String[] getAvailabledays() {
		return availabledays;
	}

	public void setAvailabledays(String[] availabledays) {
		this.availabledays = availabledays;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}
	
	public int getBath() {
		return bath;
	}

	public void setBath(int bath) {
		this.bath = bath;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

}
