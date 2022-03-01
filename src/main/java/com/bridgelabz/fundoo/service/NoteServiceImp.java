package com.bridgelabz.fundoo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.bridgelabz.fundoo.dto.NoteDTO;
import com.bridgelabz.fundoo.entity.Note;
import com.bridgelabz.fundoo.entity.NoteImage;
import com.bridgelabz.fundoo.entity.User;
import com.bridgelabz.fundoo.exception.FundooException;
import com.bridgelabz.fundoo.repository.NoteImageRepository;
import com.bridgelabz.fundoo.repository.NoteRepository;
import com.bridgelabz.fundoo.repository.UserRepository;
import com.bridgelabz.fundoo.util.S3Service;
import com.bridgelabz.fundoo.util.TokenService;

@Service
public class NoteServiceImp implements NoteService {

	@Autowired
	private TokenService tokenService;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NoteRepository noteRepository;
	
	@Autowired
	private S3Service s3service;
	
	@Autowired
	private NoteImageRepository noteImageRepository;
	
	@Autowired
	private Map<Long, User> cacheMap;

	@Override
	@Transactional
	//@CachePut(value = "notes",key = "#token")
	public Note createNote(NoteDTO noteDTO, String token) {
		Long userId = decodeToken(token);
		User user = getUser(userId);
		Note note = new Note();
		BeanUtils.copyProperties(noteDTO, note);
		note = noteRepository.save(note);
		user.getNotes().add(note);
//		if(note != null) {
//			throw new FundooException(HttpStatus.BAD_GATEWAY.value(), "Exception While creating note");
//		}
		userRepository.save(user);
		return note;
	}

	private Long decodeToken(String token) {
		return tokenService.decodeToken(token);
	}

	private User getUser(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "User Not Found"));
	}
	
	private Note getNote(User user, Long noteId) {
		return user.getNotes().stream().filter(note -> note.getId().equals(noteId)).findFirst()
		.orElseThrow(() -> new FundooException(HttpStatus.NOT_FOUND.value(), "Note not Found"));
	}

	@Override
	public Note updateNote(String token, Long noteId, NoteDTO noteDTO) {
		User user = getUser(decodeToken(token));
		Note note = getNote(user, noteId);
		BeanUtils.copyProperties(noteDTO, note);
		return noteRepository.save(note);
	}

	@Override
	public Note pinNote(String token, Long noteId) {
		User user = getUser(decodeToken(token));
		Note note = getNote(user, noteId);
		if(note.getIsPinned() == true)
			note.setIsPinned(false);
		else
			note.setIsPinned(true);	
		return noteRepository.save(note);
	}

	@Override
	//@Cacheable(value = "notes",key = "#token")
	public List<Note> getAllNotes(String token) {
		Long userId = decodeToken(token);
		if(cacheMap.containsKey(userId)) {
			System.out.println("From Cache");
			return cacheMap.get(userId).getNotes();
		}
		System.out.println("From Database");
		User user = getUser(userId);
		cacheMap.put(userId, user);
		return user.getNotes();
	}

	@Override
	public Note getNote(String token, Long noteId) {
		User user = getUser(decodeToken(token));
		return getNote(user, noteId);
	}

	@Override
	public Note addImageToNote(String token, Long noteId, MultipartFile file) {
		User user = getUser(decodeToken(token));
		Note note = getNote(user, noteId);
		String noteImageURL = s3service.fileUpload(file, "note_images", note.getId().toString());
		NoteImage noteImage = new NoteImage();
		noteImage.setImageURL(noteImageURL);
		noteImage = noteImageRepository.save(noteImage);
		note.getNoteImages().add(noteImage);
		return noteRepository.save(note);
	}
	

}
