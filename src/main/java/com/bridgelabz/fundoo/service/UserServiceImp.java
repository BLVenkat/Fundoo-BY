package com.bridgelabz.fundoo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.LoginDTO;
import com.bridgelabz.fundoo.dto.UserDTO;
import com.bridgelabz.fundoo.entity.User;
import com.bridgelabz.fundoo.exception.FundooException;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.util.EmailService;
import com.bridgelabz.fundoo.util.S3Service;
import com.bridgelabz.fundoo.util.TokenService;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenService tokenService;

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private S3Service s3Service;
	

	@Value("${spring.mail.username}")
	private String senderEmailId;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public User register(UserDTO userDTO) {
		Optional<User> isUserPresent = userRepository.findByEmail(userDTO.getEmail());
		if (isUserPresent.isPresent()) {
			throw new FundooException(HttpStatus.CONFLICT.value(), "Email is already registered");
		}
		User user = new User();
		BeanUtils.copyProperties(userDTO, user);
		user.setIsEmailVerified(false);
		user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
		User savedUser = userRepository.save(user);
		String token = tokenService.createToken(savedUser.getId(), new Date(System.currentTimeMillis() + (180 * 1000)));
		emailService.sendMail("Email Verification For Fundoo", "http://localhost:8080/user/verify-email/" + token,
				savedUser.getEmail(), senderEmailId);
		return savedUser;
	}

	@Override
	public String login(LoginDTO loginDTO) {
		User user = userRepository.findByEmail(loginDTO.getEmail())
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "Email is not Registered"));
		if (!user.getIsEmailVerified())
			throw new FundooException(HttpStatus.UNAUTHORIZED.value(), "Email is not verified");

		if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
			throw new FundooException(HttpStatus.UNAUTHORIZED.value(), "password is incorrect");
		return tokenService.createToken(user.getId());
	}

	@Override
	public User verifyEmail(String token) {
		Long userId = tokenService.decodeToken(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		user.setIsEmailVerified(true);
		return userRepository.save(user);
	}

	@Override
	public void forgotPassword(String emailId) {
		User user = userRepository.findByEmail(emailId)
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		String token = tokenService.createToken(user.getId());
		emailService.sendMail("Reset Password Link", "http://localhost:8080/user/reset-password/" + token,
				user.getEmail(), senderEmailId);
	}

	@Override
	public User resetPassword(String token, String password) {
		Long userId = tokenService.decodeToken(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		user.setPassword(passwordEncoder.encode(password));
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}
	
	@Override
	public String uploadProfilePic(String token, MultipartFile file) {
		Long userId = tokenService.decodeToken(token);
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
		String profileURL =  s3Service.fileUpload(file, "UserProfileImages", userId.toString());
		user.setProfileURL(profileURL);
		userRepository.save(user);
		return profileURL;

	}
	
}
