package com.dantemartins.workshopmongo.services;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantemartins.workshopmongo.domain.Comment;
import com.dantemartins.workshopmongo.dto.CommentDTO;
import com.dantemartins.workshopmongo.repository.CommentRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository repo;
	
	public List<Comment> findAll() {
		return repo.findAll();
	}	
	
	public Comment insert(Comment obj) {
		return repo.insert(obj);
	}
	
	public Comment fromDTO(CommentDTO objDto) {
		return new Comment(objDto.getId(),objDto.getText(),objDto.getDate());
	}

	public List<Comment> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 60 * 1000);
		System.out.println("maxDate = " + maxDate);
		return repo.fullSearch(text, minDate, maxDate);
	}
}