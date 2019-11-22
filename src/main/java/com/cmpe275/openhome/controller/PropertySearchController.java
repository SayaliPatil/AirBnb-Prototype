package com.cmpe275.openhome.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.PropertyRepository;
import com.cmpe275.openhome.service.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class PropertySearchController {
    
    @Autowired
    PropertyService propertyService;
    private Long id;
   

    @ResponseBody
    @GetMapping("/allproperties")
    public List<Property> getProperties() {
        return propertyService.getAllProperties();
    }

    @ResponseBody
    @PostMapping("/results")
    List<Property> getResults(@Valid @RequestBody Property prop) {
    	return propertyService.getAllResults(prop);
    }
     
    @ResponseBody
    @PostMapping("/search")
    public Property getPropertyById(@RequestBody Long id) {
        this.id = id;
        return propertyService.getPropertyById(id);
    }
    
    @ResponseBody
    @RequestMapping(method=RequestMethod.GET, value = "/booking/{id}")
    public ResponseEntity<Property> getBookingDetails(@PathVariable Long id) {
    	Property prop = propertyService.getPropertyById(id);
    	return ResponseEntity.ok(prop);
    }
}
