//package com.bridgelabz.fundoo.controller;
//
//import static org.mockito.Mockito.when;
//import static org.mockito.ArgumentMatchers.any;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.springframework.validation.BindingResult;
//
//import com.bridgelabz.fundoo.dto.LoginDTO;
//import com.bridgelabz.fundoo.dto.UserDTO;
//import com.bridgelabz.fundoo.entity.User;
//import com.bridgelabz.fundoo.response.Response;
//import com.bridgelabz.fundoo.service.UserServiceImp;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@ExtendWith(SpringExtension.class)
//@WebMvcTest(controllers = UserController.class,excludeAutoConfiguration = SecurityAutoConfiguration.class)
//public class UserControllerTest {
//
//	@Autowired
//	private MockMvc mockMvc;
//	
//	@MockBean
//	private UserServiceImp userService;
//	
//	@MockBean
//	private BindingResult bindingResult;
//	
//	private LoginDTO loginDto;
//	
//	private ObjectMapper objectMapper;
//	
//	private UserDTO userDto;
//	
//	private UserDTO improperUserDto;
//	
//	private User user;
//	
//	@BeforeEach
//	public void setup() {
//		loginDto =  new LoginDTO("venky@gmail.com", "admin@123");
//		userDto = new UserDTO("Venkat", "Reddy", "9876543210", "venky@gmail.com", "admin@123");
//		improperUserDto = new UserDTO(null, "Reddy", "9876543210", "venky@gmail.com", "admin@123");
//		objectMapper = new ObjectMapper();
//		user = new User(1L, "Venkat", "Reddy", "9876543210", "venky@gmail.com", "admin@123", null, null, null, null);
//	}
//	
//	@Test
//	public void registerTest() throws Exception {
//		when(userService.register(any())).thenReturn(user);
//		String content = objectMapper.writeValueAsString(userDto);
//		MvcResult result =  mockMvc.perform(post("/user/register").content(content).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//		String responseAsString = result.getResponse().getContentAsString();
//		System.out.println("Response: "+responseAsString);
//		Response  response = objectMapper.readValue(responseAsString, Response.class);
//		Assertions.assertEquals(201,response.getStatusCode());
//
//	}
//	
//	@Test
//	public void registerInvalidDTOTest() throws Exception {
//		when(bindingResult.hasErrors()).thenReturn(true);
//		String content = objectMapper.writeValueAsString(improperUserDto);
//		MvcResult result =  mockMvc.perform(post("/user/register").content(content).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//		String responseAsString = result.getResponse().getContentAsString();
//		System.out.println("Response: "+responseAsString);
//		Response  response = objectMapper.readValue(responseAsString, Response.class);
//		Assertions.assertEquals(422,response.getStatusCode());
//
//	}
//	
//	@Test
//	void loginTest() throws Exception {
//		when(userService.login(any())).thenReturn("token");
//		String content = objectMapper.writeValueAsString(loginDto);
//		MvcResult result =  mockMvc.perform(post("/user/login").content(content).contentType(MediaType.APPLICATION_JSON_VALUE)).andReturn();
//		String responseAsString = result.getResponse().getContentAsString();
//		System.out.println("Response: "+responseAsString);
//		Response  response = objectMapper.readValue(responseAsString, Response.class);
//		Assertions.assertEquals(200,response.getStatusCode());
//	}
//	
//	@Test
//	void GetAllUsersTest() throws Exception {
//		when(userService.getAllUser()).thenReturn(new ArrayList<User>());
//		MockHttpServletRequestBuilder request =  MockMvcRequestBuilders.get("/user");	
//		MvcResult result =  mockMvc.perform(request).andReturn();
//		String responseAsString = result.getResponse().getContentAsString();
//		System.out.println("Response: "+responseAsString);
//		Response  response = objectMapper.readValue(responseAsString, Response.class);
//		Assertions.assertEquals(200,response.getStatusCode());
//	}
//}
