package com.cmpe275.openhome.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.cmpe275.openhome.model.Booking;
import com.cmpe275.openhome.model.Filter;
import com.cmpe275.openhome.model.Property;
import com.cmpe275.openhome.repository.UserRepository;
import com.cmpe275.openhome.service.BookingService;
import com.cmpe275.openhome.service.DashboardService;
import com.cmpe275.openhome.service.PropertyService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", allowCredentials = "true")
public class DashboardController {

	@Autowired
	UserRepository userRepository;

	@Autowired
    private BookingService bookingService;
	
	@Autowired
    private PropertyService propertyService;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private DashboardService dashboardService;
	
	@ResponseBody
    @RequestMapping(method=RequestMethod.GET, value = "/fetchUserDashBoard/{email}")
    public ResponseEntity<?> getUserDashboard(@PathVariable String email) {
		System.out.println("User ID send as a parm : " +email);
		return ResponseEntity.ok(dashboardService.getDashboardDetails(email));
    }
	
	@ResponseBody
    @RequestMapping(method=RequestMethod.GET, value = "/fetchHostDashBoard/{email}")
    public ResponseEntity<?> getHostDashboard(@PathVariable String email) {
    	System.out.println("User ID send as a parm : " +email);
    	List<Property> property = propertyService.getPropertyDetails(email);
    	return ResponseEntity.ok(property);
    }
	
	@PostMapping("/user/fetchBillingDetails")
    @ResponseBody
    public ResponseEntity<?> fetchUserBillingDetails(@Valid @RequestBody Filter filter) throws URISyntaxException {
		System.out.println("User ID send as a parm : " +filter.getEmail()+ " " +filter.getMonth() + " " +filter.getId());
		return ResponseEntity.ok(dashboardService.getMonthlyDashboardDetails(filter));
    }
	
	@PostMapping("/user/fetchHostDashBoard")
    @ResponseBody
    public ResponseEntity<?> fetchHostBillingDetails(@Valid @RequestBody Filter filter) throws URISyntaxException {
		System.out.println("User ID send as a parm : " +filter.getEmail()+ " " +filter.getMonth() + " " +filter.getId());
		return ResponseEntity.ok(dashboardService.getHostDashboardDetails(filter));
    }
	
//	@ResponseBody
//    @RequestMapping(method=RequestMethod.GET, value = "/user/fetchBillingDetails/{email}")
//    public ResponseEntity<?> getGuestMonthlyBillingInfo(@PathVariable String email) {
//		System.out.println("User ID send as a parm : " +email);
//		String[] queryParameter = email.split("&");
//		return ResponseEntity.ok(dashboardService.getMonthlyDashboardDetails(queryParameter[0], queryParameter[1]));
//    }
}