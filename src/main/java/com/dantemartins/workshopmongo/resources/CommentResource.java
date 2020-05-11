package com.dantemartins.workshopmongo.resources;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.dantemartins.workshopmongo.domain.Comment;
import com.dantemartins.workshopmongo.domain.Post;
import com.dantemartins.workshopmongo.dto.CommentDTO;
import com.dantemartins.workshopmongo.resources.util.URL;
import com.dantemartins.workshopmongo.services.CommentService;

@RestController
@RequestMapping(value="/comments")
public class CommentResource {

	@Autowired
	private CommentService service;
	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<Comment>> findAll() {
		List<Comment> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(method=RequestMethod.POST)
 	public ResponseEntity<Void> insert(@RequestBody CommentDTO objDto) {
		Comment obj = service.fromDTO(objDto);
		obj         = service.insert(obj);
		return ResponseEntity.noContent().build();
		//URI uri     = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		//return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/fullsearch", method=RequestMethod.GET)
 	public ResponseEntity<List<Comment>> fullSearch(
 			@RequestParam(value="text"   , defaultValue="") String text,
 			@RequestParam(value="minDate", defaultValue="") String minDate,
 			@RequestParam(value="maxDate", defaultValue="") String maxDate) {
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Comment> list = service.fullSearch(text, min, max);
		return ResponseEntity.ok().body(list);
	}
}