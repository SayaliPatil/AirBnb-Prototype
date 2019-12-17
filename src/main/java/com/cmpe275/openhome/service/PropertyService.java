package com.cmpe275.openhome.service;

import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
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

import org.apache.commons.codec.language.DoubleMetaphone;
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
	
	@Autowired
	private DateUtility dateUtility;
	
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
		List<String> days = new ArrayList<>();
		SimpleDateFormat  simpleDateformat = new SimpleDateFormat("EEEE"); // the day of the week spelled out completely
		
		Date current = prop.getStartdate();
		 
	    while (current.before(prop.getEnddate())) {
	    	days.add("%" + simpleDateformat.format(current) + "%");
	 
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(current);
	        calendar.add(Calendar.DATE, 1);
	        current = calendar.getTime();
	    }
	    DoubleMetaphone dm = new DoubleMetaphone();
	    dm.setMaxCodeLen(5);
	    
	    StringBuilder daysclause = new StringBuilder();
	    for (int i = 0; i < days.size(); i++) {
	    	daysclause.append((daysclause.length() == 0) ? " p.availabledays like ?" : " AND p.availabledays like ?");
	    	daysclause.append(i);
	    }
	    
	    for(String string: days)
	    	System.out.println("Day:" + string);
	    
	    StringBuilder daysprice = new StringBuilder();
	    if(days.contains("%Sunday%") || days.contains("%Saturday%") || days.contains("%Friday%"))
	    {
	    	daysprice.append("(:minprice is null OR p.weekendprice >= :minprice) "
	    			+ "AND (:maxprice is null OR p.weekendprice <= :maxprice) AND");
	    }
	    if(days.contains("%Monday%") || days.contains("%Tuesday%") || days.contains("%Wednesday%") || days.contains("%Thursday%"))
	    {
	    	daysprice.append("(:minprice is null OR p.weekdayprice >= :minprice) "
	    			+ "AND (:maxprice is null OR p.weekdayprice <= :maxprice) AND");
	    }

	    
	    String jpaql = "from Property as p WHERE (UPPER(p.address) LIKE CONCAT('%',UPPER(:address),'%'))"
	    		+ " AND (p.is_deleted = false) AND (p.startdate <= :startdate) "
	    		+ "AND (p.enddate >= :enddate) AND (:description is null OR (UPPER(p.description) LIKE CONCAT('%',UPPER(:description),'%'))) "
	    		+ "AND (:wifi is null OR p.wifi =:wifi) AND (:proptype is null OR p.proptype =:proptype) "
	    		+ "AND (:sharingtype is null OR p.sharingtype =:sharingtype) AND " + daysprice.toString() + daysclause.toString();
	    System.out.println(jpaql);
		Query query = entityManager.createQuery(jpaql);	
		query.setParameter("address",prop.getAddress());
		query.setParameter("enddate",prop.getEnddate(), TemporalType.TIMESTAMP);
		query.setParameter("startdate",prop.getStartdate(), TemporalType.TIMESTAMP);
		System.out.println("StartDate passed through property search page : " +prop.getStartdate());
		for (int i = 0; i < days.size(); i++) {
            query.setParameter(i, days.get(i));
        }
		
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
	    	boolean flag = bookingService.getBookingDetailsById(property , dateUtility.getStringDate(prop.getStartdate()), dateUtility.getStringDate(prop.getEnddate()) );
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