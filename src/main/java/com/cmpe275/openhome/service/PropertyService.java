package com.cmpe275.openhome.service;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.Query;

import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.PropertyRepository;

import org.springframework.beans.factory.annotation.Autowired;

@Service
public class PropertyService {

	@Autowired
	private PropertyRepository propertyRepository;

	@Autowired
	private EntityManager entityManager;
	
	/**
	 * Fetches all properties
	 * 
	 * @return List of properties
	 */
	public List<Property> getAllProperties() {
		List<Property> propList = new ArrayList<Property>();
		System.out.println("Return the lists : " + propertyRepository.count());
		propList = propertyRepository.findAll();
		// propertyRepository.findAll().forEach(propList::add);
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
}