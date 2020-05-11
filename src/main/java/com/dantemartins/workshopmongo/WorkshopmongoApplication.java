package com.dantemartins.workshopmongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Versão com inclusão do metodo Post para os post(entidade post)
// e com inclusão de comentários
// e com filtro de comentarios no fullsearch

@SpringBootApplication
public class WorkshopmongoApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkshopmongoApplication.class, args);
	}
}