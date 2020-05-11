package com.dantemartins.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.dantemartins.workshopmongo.domain.Comment;
import com.dantemartins.workshopmongo.domain.Post;
import com.dantemartins.workshopmongo.domain.User;
import com.dantemartins.workshopmongo.dto.AuthorDTO;
import com.dantemartins.workshopmongo.dto.CommentDTO;
import com.dantemartins.workshopmongo.dto.PostDTO;
import com.dantemartins.workshopmongo.repository.CommentRepository;
import com.dantemartins.workshopmongo.repository.PostRepository;
import com.dantemartins.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository    userRepository;

	@Autowired
	private PostRepository    postRepository;
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public void run(String... arg0) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		commentRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex  = new User(null, "Alex Green" , "alex@gmail.com");
		User bob   = new User(null, "Bob Grey"   , "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));

		Post post1 = new Post(null, sdf.parse("01/05/2020"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("02/05/2020"), "Bom dia"      , "Acordei feliz hoje!"                , new AuthorDTO(maria));
		Post post3 = new Post(null, sdf.parse("03/05/2020"), "Teste 12345"  , "Teste 123"                          , new AuthorDTO(bob));

		postRepository.saveAll(Arrays.asList(post1, post2, post3));

		Comment c1 = new Comment(null ,"Boa viagem mano!"   , sdf.parse("01/05/2020"));
		Comment c2 = new Comment(null ,"Aproveite"          , sdf.parse("02/05/2020"));
		Comment c3 = new Comment(null ,"Tenha um ótimo dia!", sdf.parse("03/05/2020"));
		Comment c4 = new Comment(null ,"Teste!"             , sdf.parse("04/03/2020"));
		
		post1.getComments().add(c1);
		post1.getComments().add(c2);
		//post1.getComments().addAll(Arrays.asList(c1, c2));
		post2.getComments().addAll(Arrays.asList(c3));
		post3.getComments().addAll(Arrays.asList(c4));
		commentRepository.saveAll(Arrays.asList(c1, c2, c3, c4));
		
		postRepository.saveAll(Arrays.asList(post1, post2, post3));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));
		bob.getPosts().addAll(Arrays.asList(post3));
		
		userRepository.saveAll(Arrays.asList(maria,bob));
		
		//System.out.println(maria);
		//System.out.println(post1);
		//System.out.println(c1);
		//System.out.println(c2);
	}
}