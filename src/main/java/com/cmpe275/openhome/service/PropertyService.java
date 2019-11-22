package com.cmpe275.openhome.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;
import javax.persistence.TemporalType;

import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.PropertyRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private EntityManager entityManager;
	
	private List<Property> propList;
	/**
	 * Fetches all properties
	 * 
	 * @return List of properties
	 */
	
	
	public List<Property> getAllProperties() {
		System.out.println("size of proplist : " + propList.size());
		System.out.printf("inside getAllProperties", propList);
		return propList;
	}

	public Property getPropertyById(Long id) {

		System.out.println("search lists count: " + propertyRepository.count());
		System.out.printf("inside getById", id);
		// return propertyRepository.findOne();
		Property prop = null;
		Query query = entityManager.createQuery("from Property as p WHERE p.id=:id");
	    query.setParameter("id",id);
	    System.out.println(query.getParameterValue("id"));
	    try {
			prop = (Property) query.getSingleResult();
			System.out.printf("inside getprop "+prop);
	    } catch (Exception e) {
	        System.out.println("Here! Inside getPropertyById");
	    }
	 return prop;
	}
	
	public List<Property> getAllResults(Property prop) {
//		List<Property> propList = new ArrayList<Property>();
		//Query query = entityManager.createQuery("from Property as p WHERE p.startdate <= startdate AND p.enddate >= enddate");
		Query query = entityManager.createQuery("from Property as p WHERE UPPER(p.address) LIKE CONCAT('%',UPPER(:address),'%') AND p.startdate <= :startdate AND p.enddate >= :enddate");
	    query.setParameter("address",prop.getAddress());
	    query.setParameter("enddate",prop.getEnddate(), TemporalType.TIMESTAMP);
	    query.setParameter("startdate",prop.getStartdate(), TemporalType.TIMESTAMP);
//	    System.out.println(query.getParameterValue("address"));
//	    System.out.println(query.getParameterValue("startdate"));
//	    System.out.println(query.getParameterValue("enddate"));
	    try {
			propList = (List<Property>)query.getResultList();
			System.out.printf("inside getAllResults "+propList);
	    } catch (Exception e) {
	        System.out.println("Here! Inside getAllResults");
	    }
	 return propList;
	}
}