package com.gestion_entreprise.gestion_entreprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@SpringBootApplication

public class GestionEntrepriseApplication {

	public static void main(String[] args) {

		SpringApplication.run(GestionEntrepriseApplication.class, args);
	}

}