package com.cmpe275.openhome.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.PropertyRepository;

@Service
public class PropertyUploadService {
	
	@Autowired
	private PropertyRepository propertyRepository;
	
	@Autowired
	private EntityManager entityManager;
	
	private static final String ERROR_IN_UPLOADING_PROPERTY = "Error in Uploading property";
	private static final String FETCH_PROPERTY_DETAILS_EXCEPTION_MESSAGE = "No property details found for the host";
	
	public Property uploadProperty(Property prop) {
		return propertyRepository.save(prop);
	}

}
