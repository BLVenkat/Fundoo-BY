package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	@ApiModelProperty(example = "Jhon", required = true, notes = "First name of user",dataType = "String")
	@NotBlank(message = "Firstname cannot be blank")
	private String firstName;
	
	@NotBlank(message = "Lastname cannot be blank")
	private String lastName;
	
	@NotBlank(message = "phone number cannot be blank")
	@Length(min = 10,max = 10,message = "Phone number should be 10 digits")
	private String phoneNumber;
	
	@Email(message = "Email is not proper")
	private String email;
	
	@NotBlank(message = "password cannot be blank")
	private String password;
	
}
