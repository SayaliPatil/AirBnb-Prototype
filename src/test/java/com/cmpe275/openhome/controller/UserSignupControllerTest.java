package com.cmpe275.openhome.controller;
import java.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.test.autoconfigure.web.servlet.*;
import org.springframework.boot.test.mock.mockito.*;
import org.springframework.http.HttpStatus;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.cmpe275.openhome.model.User;
import com.cmpe275.openhome.repository.UserRepository;
import com.cmpe275.openhome.service.EmailService;
import com.cmpe275.openhome.service.UserService;
import com.cmpe275.openhome.util.utils;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserSignupController.class)
public class UserSignupControllerTest {
	@Autowired
    private MockMvc mvc;
	
	@MockBean
    private UserService userService;
	
    @MockBean
    private EmailService emailService;
    
    @MockBean
    private UserRepository userRepository;
	
	private User user;

    private User createTestUser(Long userid, String email, String firstname, String lastname,String password,Boolean verified){
        User user = new User();
        user.setID(userid);
        user.setEmail(email);
        user.setFirst_name(firstname);
        user.setLast_name(lastname);
        user.setPassword(password);
        user.setVerified(verified);
        return user;
    }

    @Before
    public void setUp(){
        user = this.createTestUser((long)12,"test_user@gmail.com","Surabhi","Sinha","secret",true);
    }

    @Test
    public void userSignUpSuccess() throws Exception {
        String uri = "/api/signup";
        Map<String, String> map = new HashMap<>();
        map.put("email","test_user@gmail.com");
        map.put("first_name","Surabhi");
        map.put("last_name","Sinha");
        map.put("password","secret");
        map.put("verified","true");
        String userDetail  = utils.mapToJson(map);
        mvc.perform(post(uri).contentType(MediaType.APPLICATION_JSON).content(userDetail)).andExpect(status().isOk());
        MvcResult result = mvc
                .perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(userDetail))
                .andReturn();
        int status = result.getResponse().getStatus();
        Assert.assertEquals("User successfully loggedin status check",200,status);
    }

    @Test
    public void userLoginSuccess() throws Exception {
        Mockito.when(userService.findByEmail("test_user@gmail.com")).thenReturn(user);
        Map<String, String> map = new HashMap<>();
        String uri = "/api/login";
        map.put("ID","12");
        map.put("email","test_user@gmail.com");
        map.put("first_name","Surabhi");
        map.put("last_name","Sinha");
        map.put("password","secret");
        map.put("verified","true");
        String userDetail  = utils.mapToJson(map);
        System.out.println(userDetail);
        MvcResult result = mvc
                .perform(post(uri)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON).content(userDetail))
                .andReturn();
        String resContent = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();
        System.out.println("sas:"+resContent);
        String expectedUserData = "{\"ID\":\"12\",\"email\":\"test_user@gmail.com\",\"first_name\":\"Surabhi\",\"last_name\":\"Sinha\",\"verified\":true}";
        Assert.assertEquals("User successfully loggedin status check",400,status);
        Assert.assertEquals("Logged in user data",expectedUserData,resContent);
    }

}
