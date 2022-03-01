package com.bridgelabz.fundoo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.LoginDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.entity.User;

public interface UserService {

	public User register(UserDTO userDTO);
	
	public String login(LoginDTO loginDTO);
	
	public User verifyEmail(String token);
	
	public void forgotPassword(String emailId);
	
	public User resetPassword(String token, String password);
	
	public List<User> getAllUser(); 
	
	public String uploadProfilePic(String token, MultipartFile file);
}
