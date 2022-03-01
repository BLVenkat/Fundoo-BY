package com.bridgelabz.fundoo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table(name = "USER_DETAILS")
@AllArgsConstructor
public class User implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(name = "FIRST_NM",columnDefinition ="varchar(20)")
	private String firstName;
	
	private String lastName;
	
	@Column(name = "PHONE_NUM",nullable = false,unique = true)
	private String phoneNumber;
	
	@Column(name = "EMAIL",nullable = false,unique = true)
	private String email;
	
	@JsonIgnore
	@Column(name = "PASSWORD",nullable = false)
	private String password;
	
	private Boolean isEmailVerified;
	
	private String profileURL;
	
	@CreationTimestamp
	private LocalDateTime createdTimeStamp;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity = Note.class)
	@JoinColumn(name = "user_id",referencedColumnName = "id")
	private List<Note> notes;
}
