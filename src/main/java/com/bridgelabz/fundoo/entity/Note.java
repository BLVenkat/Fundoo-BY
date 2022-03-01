package com.bridgelabz.fundoo.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String title;
	
	private String description;
	
	private String reminder;
	
	private String colour;
	
	private Boolean isPinned;
	
	private Boolean isArchived;
	
	private Boolean isTrashed;
	
	@CreationTimestamp
	private LocalDateTime createdTimstamp;
	
	@UpdateTimestamp
	private LocalDateTime updatedTimestamp;
	
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(targetEntity = NoteImage.class)
	@JoinColumn(name = "noteid",referencedColumnName = "id")
	private List<NoteImage> noteImages;
	
}
