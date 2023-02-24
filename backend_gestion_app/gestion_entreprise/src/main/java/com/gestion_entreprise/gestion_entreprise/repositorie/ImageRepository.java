package com.gestion_entreprise.gestion_entreprise.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gestion_entreprise.gestion_entreprise.entities.Image;

public interface ImageRepository extends JpaRepository<Image, Long> {

}
