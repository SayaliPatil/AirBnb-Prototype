package com.cmpe275.openhome.controller;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.service.AmazonClient;
import com.cmpe275.openhome.service.EmailService;
import com.cmpe275.openhome.service.PropertyUploadService;
import com.cmpe275.openhome.utils.EmailUtility;
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
	EmailService emailService;

    @Autowired
    PropertyUploadController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    
    
    @ResponseBody
    @RequestMapping(method=RequestMethod.POST, value = "/uploadProperty")
    public ResponseEntity<Property> uploadProperty(@RequestPart(value = "data") String data,@RequestPart(value = "files") MultipartFile[] files) throws JsonMappingException, JsonProcessingException{
    	String response = "";
    	ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    	Property prop;
    	
    	try {
    		prop = mapper.readValue(data, Property.class);
    		for(MultipartFile file : files)
            {
            	System.out.println("started upload");
            	response += this.amazonClient.uploadFile(file);
            	System.out.println("ended upload : " + response );
            }
            
        	prop.setImages(response);
            
            System.out.println("Images sting: " + prop.getImages());
    	}
    	catch (JsonMappingException e){
    		throw Re
    	}
    	catch{
    		throw  
    	}
        
        propertyUploadService.uploadProperty(prop);
    	return ResponseEntity.ok(prop);
    }
    
    @RequestMapping(method=RequestMethod.PATCH, value = "/updateProperty")
    public ResponseEntity<Property> updateProperty(@RequestBody Property data) throws ParseException{
        Property updated = propertyUploadService.updateProperty(data);
        String updatePropertyHost = EmailUtility.createPropertyUpdateMessageHost();
        emailService.sendEmail(updated.getHost_email(), updatePropertyHost, "Property Updated!!");
    	return ResponseEntity.ok(data);
    }
    
    @RequestMapping(method=RequestMethod.DELETE, value = "/deleteProperty")
    public ResponseEntity<Property> deleteProperty(@RequestBody Property data) throws ParseException{
        Property deleted  = propertyUploadService.deleteProperty(data);
        String deleteMessage = EmailUtility.createPropertyDeleteMessageHost();
        emailService.sendEmail(deleted.getHost_email(), deleteMessage, " Property Deleted!!");
    	return ResponseEntity.ok(data);
    }
    
    
}
