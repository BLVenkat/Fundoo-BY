package com.bridgelabz.fundoo.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {

	@NotBlank(message = "Title cannot be blank")
	private String title;

	@NotBlank(message = "Description cannot be blank")
	private String description;

	private String reminder;

	private String colour;

	private Boolean isPinned;

	private Boolean isArchived;

	private Boolean isTrashed;
}
