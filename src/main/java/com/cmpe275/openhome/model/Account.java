package com.cmpe275.openhome.model;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name = "account")
public class Account implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ID;
	
	private Long guestID;
	
	private Long hostID;
	
	private Long propertyID;
	
	private Long bookingID;
	
	private double guestAccountBalance;
	
	private double hostAccountBalance;
	
	@Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    private Date created_at;
	
	@Temporal(TemporalType.TIMESTAMP)
    @LastModifiedDate
    private Date updated_at;
	
}
