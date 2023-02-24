package com.gestion_entreprise.gestion_entreprise.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RestResource;

import com.gestion_entreprise.gestion_entreprise.entities.Categorie;

@RestResource
public interface CategorieRepositori extends JpaRepository<Categorie, Long> {

}
