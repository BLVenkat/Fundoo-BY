package com.bridgelabz.fundoo.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.NoteDTO;
import com.bridgelabz.fundoo.entity.Note;

public interface NoteService {

	public  Note createNote(NoteDTO noteDTO, String token);
	
	public Note updateNote(String token,Long noteId, NoteDTO noteDTO);
	
	public Note pinNote(String token,Long noteId);
	
	public List<Note> getAllNotes(String token);
	
	public Note getNote(String token, Long noteId);
	
	public Note addImageToNote(String token, Long noteId, MultipartFile file);
}
