package com.dantemartins.workshopmongo.services;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dantemartins.workshopmongo.domain.Post;
import com.dantemartins.workshopmongo.dto.PostDTO;
import com.dantemartins.workshopmongo.repository.PostRepository;
import com.dantemartins.workshopmongo.services.exception.ObjectNotFoundException;

@Service
public class PostService {

	@Autowired
	private PostRepository repo;
	
	public List<Post> findAll() {
		return repo.findAll();
	}	
	
	public Post findById(String id) {
		Optional<Post> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado (1)"));
	}
	
	public List<Post> findByTitle(String text) {
		return repo.searchTitle(text);
	}
	
	public Post insert(Post obj) {
		return repo.insert(obj);
	}
	
	public Post fromDTO(PostDTO objDto) {
		return new Post(objDto.getId(),objDto.getDate(),objDto.getTitle(),objDto.getBody(),objDto.getAuthor());
	}

	public List<Post> fullSearch(String text, Date minDate, Date maxDate) {
		maxDate = new Date(maxDate.getTime() + 24 * 60 * 59 * 1000 + 999);
		System.out.println("maxDate = " + maxDate);
		return repo.fullSearch(text, minDate, maxDate);
	}
}