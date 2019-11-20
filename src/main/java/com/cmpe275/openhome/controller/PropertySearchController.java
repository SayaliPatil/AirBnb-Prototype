package com.cmpe275.openhome.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.PropertyRepository;
import com.cmpe275.openhome.service.PropertyService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
// @CrossOrigin(origins = "http://localhost:3000")
@CrossOrigin(origins="*",allowedHeaders="*")
public class PropertySearchController {
    
    @Autowired
    PropertyService propertyService;
    
    @ResponseBody
    @PostMapping("/properties")
    public List<Property> getProperties(@Valid @RequestBody Property prop) {
        return propertyService.getAllProperties();
    }

    @ResponseBody
    @PostMapping("/search")
    public Property getPropertyById(@RequestBody Long id) {
        return propertyService.getPropertyById(id);
    }
}
