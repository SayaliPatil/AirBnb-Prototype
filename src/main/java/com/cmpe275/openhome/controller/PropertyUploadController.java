package com.cmpe275.openhome.controller;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe275.openhome.exception.CustomException;
import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.service.AmazonClient;
import com.cmpe275.openhome.service.PropertyService;
import com.cmpe275.openhome.service.PropertyUploadService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("")
@CrossOrigin(origins = "http://localhost:3000")
public class PropertyUploadController {

	@Autowired
    PropertyUploadService propertyUploadService;
    private Long id;
    
    private AmazonClient amazonClient;

    @Autowired
    PropertyUploadController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    private static final String BOOKING_CANCELLATION_EXCEPTION_MESSAGE = "User booking could not be cancelled";
    
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST, value = "/uploadProperty")
    public ResponseEntity<Property> uploadProperty(@RequestPart(value = "data") String data,@RequestPart(value = "files") MultipartFile[] files) throws JsonMappingException, JsonProcessingException{
    	String response = "";
    	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	Property prop;
    	
    	prop = mapper.readValue(data, Property.class);
    	
    	for(MultipartFile file : files)
        {
        	System.out.println("started upload");
        	response += this.amazonClient.uploadFile(file);
        	System.out.println("ended upload : " + response );
        }
        
    	prop.setImages(response);
        
        System.out.println("Images sting: " + prop.getImages());
        
        propertyUploadService.uploadProperty(prop);
    	return ResponseEntity.ok(prop);
    }
    
    @RequestMapping(method=RequestMethod.PATCH, value = "/updateProperty")
    public ResponseEntity<?> updateProperty(@RequestBody Property data) throws ParseException{
    	return ResponseEntity.ok(propertyUploadService.updateProperty(data));
    }
    
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, value = "/cancelProperty/{id}")
    public ResponseEntity<?> cancelProperty(@PathVariable Long id) {
    	System.out.println("User ID send as a parm : " +id);
    	try {
			propertyUploadService.cancelbooking(id);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			throw new CustomException(BOOKING_CANCELLATION_EXCEPTION_MESSAGE + e.getMessage());
		}
    	return new ResponseEntity<>("{\"status\" : \"Booking cancelled successfully.!!\"}", HttpStatus.OK);
    }
}
