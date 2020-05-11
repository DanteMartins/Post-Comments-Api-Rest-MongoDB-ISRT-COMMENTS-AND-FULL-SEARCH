package com.dantemartins.workshopmongo.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dantemartins.workshopmongo.domain.Comment;
import com.dantemartins.workshopmongo.domain.Post;
import com.dantemartins.workshopmongo.dto.PostDTO;
import com.dantemartins.workshopmongo.resources.util.URL;
import com.dantemartins.workshopmongo.services.PostService;

@RestController
@RequestMapping(value="/posts")
public class PostResource {

	@Autowired
	private PostService service;
	
	@RequestMapping(method=RequestMethod.GET)
 	public ResponseEntity<List<Post>> findAll() {
		List<Post> list = service.findAll();
		return ResponseEntity.ok().body(list);
	}

	@RequestMapping(method=RequestMethod.POST)
 	public ResponseEntity<Void> insert(@RequestBody PostDTO objDto) {
		Post obj = service.fromDTO(objDto);
		obj      = service.insert(obj);
		URI uri  = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
 	public ResponseEntity<Post> findById(@PathVariable String id) {
		Post obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@RequestMapping(value="/titlesearch", method=RequestMethod.GET)
 	public ResponseEntity<List<Post>> findByTitle(@RequestParam(value="text", defaultValue="") String text) {
		text = URL.decodeParam(text);
		List<Post> list = service.findByTitle(text);
		return ResponseEntity.ok().body(list);
	}
	
	String selPost = "N";

	@RequestMapping(value="/fullsearch", method=RequestMethod.GET)
 	public ResponseEntity<List<Post>> fullSearch(
 			@RequestParam(value="text"   , defaultValue="") String text,
 			@RequestParam(value="minDate", defaultValue="") String minDate,
 			@RequestParam(value="maxDate", defaultValue="") String maxDate) {
		text = URL.decodeParam(text);
		Date min = URL.convertDate(minDate, new Date(0L));
		Date max = URL.convertDate(maxDate, new Date());
		List<Post> list    = service.fullSearch(text, min, max);
		List<Post> listRet = new ArrayList<>();
		
		System.out.println("....");
		System.out.println("....");
		System.out.println("....");
		
		String selPost = "N";
		for (Post obj : list) {
			System.out.println(obj.getComments());
			List<Comment> comment =  obj.getComments();
			for (Comment lcomment : comment) {
				if (lcomment.getText().contains(text)) {
					selPost = "S";
					listRet.add(obj);
				}
				System.out.println("selPost .... " + selPost);
			}
		}

		return ResponseEntity.ok().body(listRet);
	}
}