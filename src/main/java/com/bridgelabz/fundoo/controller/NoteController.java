package com.bridgelabz.fundoo.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.NoteDTO;
import com.bridgelabz.fundoo.entity.Note;
import com.bridgelabz.fundoo.exception.FundooException;
import com.bridgelabz.fundoo.response.Response;
import com.bridgelabz.fundoo.service.NoteService;

@RestController
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private NoteService noteService;

	@PostMapping
	public ResponseEntity<Response> createNote(@Valid @RequestBody NoteDTO noteDTO, @RequestHeader String token,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new FundooException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
					bindingResult.getAllErrors().get(0).getDefaultMessage());
		Note note = noteService.createNote(noteDTO, token);
		return new ResponseEntity<Response>(new Response(HttpStatus.CREATED.value(), "Note Created Successfully", note),
				HttpStatus.CREATED);
	}

	@PutMapping("/{noteId}")
	public ResponseEntity<Response> updateNote(@Valid @RequestBody NoteDTO noteDTO, @RequestHeader String token,
			@PathVariable Long noteId, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			throw new FundooException(HttpStatus.UNPROCESSABLE_ENTITY.value(),
					bindingResult.getAllErrors().get(0).getDefaultMessage());
		Note note = noteService.updateNote(token, noteId, noteDTO);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Note Updated Successfully", note),
				HttpStatus.OK);
	}
	
	@PutMapping(value = {"/pin/{noteId}","/un-pin/{noteId}"})
	public ResponseEntity<Response> changePin( @RequestHeader String token,
			@PathVariable Long noteId) {
		Note note = noteService.pinNote(token, noteId);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Note Updated Successfully", note),
				HttpStatus.OK);
	}
	
	@GetMapping()
	public ResponseEntity<Response> getAllNotes( @RequestHeader String token) {
		List<Note> notes = noteService.getAllNotes(token);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Notes Retrived Successfully", notes),
				HttpStatus.OK);
	}
	
	@GetMapping(value = "/{noteId}")
	public ResponseEntity<Response> getAllNotes( @RequestHeader String token, @PathVariable Long noteId) {
		Note note = noteService.getNote(token, noteId);
		return new ResponseEntity<Response>(new Response(HttpStatus.OK.value(), "Note Retrived Successfully", note),
				HttpStatus.OK);
	}
	
	@PostMapping(value = "/note-image/{noteId}")
	public ResponseEntity<Response> addImageToNote( @RequestHeader String token, @PathVariable Long noteId, @RequestBody MultipartFile file) {
		Note note = noteService.addImageToNote(token, noteId, file);
		return new ResponseEntity<Response>(new Response(HttpStatus.CREATED.value(), "Image Added To Note Successfully", note),
				HttpStatus.CREATED);
	}
	
}
