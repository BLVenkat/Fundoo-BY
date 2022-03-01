//package com.bridgelabz.fundoo.service;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.Mockito.doNothing;
//import static org.mockito.Mockito.when;
//
//import java.time.LocalDateTime;
//import java.util.Optional;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.bridgelabz.fundoo.dto.LoginDTO;
//import com.bridgelabz.fundoo.dto.UserDTO;
//import com.bridgelabz.fundoo.entity.User;
//import com.bridgelabz.fundoo.exception.FundooException;
//import com.bridgelabz.fundoo.repository.UserRepository;
//import com.bridgelabz.fundoo.util.EmailService;
//import com.bridgelabz.fundoo.util.TokenService;
//
//@ExtendWith(MockitoExtension.class)
//public class UserServiceTest {
//
//	@Mock
//	private UserRepository userRepository;
//	
//	@Mock
//	private PasswordEncoder passwordEncoder;
//	
//	@Mock
//	private TokenService tokenService;
//
//	@Mock
//	private EmailService emailService;
//
//	@InjectMocks
//	private UserServiceImp userService;
//	
//	private User user;
//	
//	private User notVerfiedUser;
//	
//	private UserDTO userDto;
//	
//	private LoginDTO loginDto;
//	
//	@BeforeEach
//	void setup() {
//		user = new User(1L,"Venkat","Reddy","9876543210","venky@gmail.com","admin@123",true,"",LocalDateTime.now(),null);
//		notVerfiedUser = new User(1L,"Venkat","Reddy","9876543210","venky@gmail.com","admin@123",false,"",LocalDateTime.now(),null);
//		userDto= new UserDTO("Venkat","Reddy","9876543210","venky@gmail.com","admin@123");
//		loginDto = new LoginDTO("venkat@gmail.com","admin@123");
//	}
//	
//	@Test
//	void registerTest() {
//		when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(null));
//		when(passwordEncoder.encode(any())).thenReturn("encoded password");
//		when(userRepository.save(any())).thenReturn(user);
//		when(tokenService.createToken(any(),any())).thenReturn("token");
//		doNothing().when(emailService).sendMail(any(), any(), any(), any());
//		User user = userService.register(userDto);
//		Assertions.assertEquals(this.user, user);	
//	}
//	
//	@Test
//	void registerExceptionTest() {
//		when(userRepository.findByEmail(any())).thenReturn(Optional.of(user));
//		try {
//			userService.register(userDto);
//		}catch (FundooException e) {
//			Assertions.assertEquals(409, e.getStatusCode());	
//		}
//		
//	}
//	
//	@Test
//	void loginTest() {
//		when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
//		when(passwordEncoder.matches(any(), any())).thenReturn(true);
//		when(tokenService.createToken(any())).thenReturn("Login Token");
//		String token = userService.login(loginDto);
//		Assertions.assertEquals("Login Token",token);
//	}
//	
//	@Test
//	void loginUserNotFoundTest() {
//		when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(null));
//		try {
//			userService.login(loginDto);
//		}catch (FundooException e) {
//			Assertions.assertEquals(404, e.getStatusCode());	
//		}
//	}
//	
//	@Test
//	void loginUserNotVerifiedTest() {
//		when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(notVerfiedUser));
//		try {
//			userService.login(loginDto);
//		}catch (FundooException e) {
//			Assertions.assertEquals(401, e.getStatusCode());	
//		}
//	}
//	
//	@Test
//	void loginUserPasswordInCorrectTest() {
//		when(userRepository.findByEmail(any())).thenReturn(Optional.ofNullable(user));
//		when(passwordEncoder.matches(any(), any())).thenReturn(false);
//		try {
//			userService.login(loginDto);
//		}catch (FundooException e) {
//			Assertions.assertEquals(401, e.getStatusCode());	
//		}
//	}
//}
