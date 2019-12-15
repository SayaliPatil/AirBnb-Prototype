package com.cmpe275.openhome.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.PropertyRepository;
import com.cmpe275.openhome.utils.DateUtility;

import org.hibernate.Cache;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private BookingService bookingService;
	
	private static final String ERROR_IN_FETCHING_RESULT = "Error in fetching result";
	private static final String FETCH_PROPERTY_DETAILS_EXCEPTION_MESSAGE = "No property details found for the host";
	private List<Property> propList;
	/**
	 * Fetches all properties
	 * 
	 * @return List of properties
	 */
	
	
	public List<Property> getAllProperties() {
//		System.out.println("size of proplist : " + propList.size());
//		System.out.printf("inside getAllProperties", propList);
		return propList;
	}

	public Property getPropertyById(Long id) {
		System.out.printf("inside getPropertyById : ", id);
		Property prop = null;
		Query query = entityManager.createQuery("from Property as p WHERE p.id=:id");
	    query.setParameter("id",id);
//	    System.out.println(query.getParameterValue("id"));
	    try {
			prop = (Property) query.getSingleResult();
			System.out.printf("inside getprop : "+prop);
	    } catch (Exception e) {
	        System.out.println("Here! Inside getPropertyById");
	    }
	 return prop;
	}
	
	public List<Property> getAllResults(Property prop) {
//		List<Property> propList = new ArrayList<Property>();

		//Query query = entityManager.createQuery("from Property as p WHERE p.startdate <= startdate AND p.enddate >= enddate");
//		Query query = entityManager.createQuery("from Property as p WHERE UPPER(p.address) LIKE CONCAT('%',UPPER(:address),'%') AND p.startdate <= :startdate AND p.enddate >= :enddate");
	
		Query query = entityManager.createQuery("from Property as p WHERE (UPPER(p.address) LIKE CONCAT('%',UPPER(:address),'%')) AND (p.is_deleted = false)AND (p.booked_flag =:flag) AND (p.startdate <= :startdate) AND (p.enddate >= :enddate) AND (:description is null OR (UPPER(p.description) LIKE CONCAT('%',UPPER(:description),'%'))) AND (:wifi is null OR p.wifi =:wifi) AND (:proptype is null OR p.proptype =:proptype) AND (:sharingtype is null OR p.sharingtype =:sharingtype) AND (:minprice is null OR p.price >= :minprice) AND (:maxprice is null OR p.price <= :maxprice)");	
		query.setParameter("address",prop.getAddress());
		query.setParameter("flag",false);
		query.setParameter("enddate",prop.getEnddate(), TemporalType.TIMESTAMP);
		query.setParameter("startdate",prop.getStartdate(), TemporalType.TIMESTAMP);
		
		if(prop.getWifi().isEmpty())
			query.setParameter("wifi", null);
		else if (!prop.getWifi().isEmpty())
			query.setParameter("wifi", prop.getWifi());
		
		if(prop.getSharingtype().isEmpty())
			query.setParameter("sharingtype", null);
		else
			query.setParameter("sharingtype",prop.getSharingtype());
		
		if(prop.getProptype().isEmpty())
			query.setParameter("proptype", null);
		else
			query.setParameter("proptype", prop.getProptype());
		
		if(prop.getDescription().isEmpty())
			query.setParameter("description", null);
		else
			query.setParameter("description", prop.getDescription());

		if(prop.getMinprice() == 0)
			query.setParameter("minprice", null);
		else
			query.setParameter("minprice", prop.getMinprice());
		
		if(prop.getMaxprice() == 0)
			query.setParameter("maxprice", null);
		else
			query.setParameter("maxprice", prop.getMaxprice());
		

	    System.out.printf("minprice :", query.getParameterValue("minprice"));
	    
	    try {
			propList = (List<Property>)query.getResultList();
			System.out.printf("inside getAllResults "+propList);
	    } catch (Exception e) {
	        System.out.println("Here! Inside getAllResults");
	        throw new CustomException(ERROR_IN_FETCHING_RESULT);
	    }
	    List<Property> temp = new ArrayList<>();
	    for(Property property : propList) {
	    	boolean flag = bookingService.getBookingDetailsById(property , DateUtility.getStringDate(prop.getStartdate()), DateUtility.getStringDate(prop.getEnddate()) );
	    	System.out.println("FLAG : " +flag);
	    	if(!flag) {
	    		temp.add(property);
	    	}
	    }
	    System.out.println("TEMP LIST : " +temp);
	    return temp;
	}
	
	public void savePropertyDetails(Property prop) {
		// TODO Auto-generated method stub
		propertyRepository.save(prop);
	}
	
	public List<Property> getPropertyDetails(String email) {
		System.out.println("booking details fetched: " +email);
		Query query = entityManager.createQuery("from Property as p WHERE (p.host_email =:email AND p.is_deleted = false)");
		query.setParameter("email", email);
		List<Property> property = null;
		try {
			property = (List<Property>) query.getResultList();
		}
		catch(Exception exception) {
			throw new CustomException(FETCH_PROPERTY_DETAILS_EXCEPTION_MESSAGE + exception.getMessage());
		}
		return property;
	}
	
//	public List<Property> findNotBookedProperty(Property prop, Date startDate, Date endDate) {
//		List<Booking> booking = bookingService.getBookingDetailsById(prop);
//		return null;
//	}
}