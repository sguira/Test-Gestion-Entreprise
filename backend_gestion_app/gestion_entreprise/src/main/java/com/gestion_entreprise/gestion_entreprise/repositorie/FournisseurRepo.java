package com.gestion_entreprise.gestion_entreprise.repositorie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import com.gestion_entreprise.gestion_entreprise.entities.Fournisseur;

@RestResource
public interface FournisseurRepo extends JpaRepository<Fournisseur, Long> {

    @Query(value = "SELECT count(id) from fournisseur", nativeQuery = true)
    public int get_fournisseur();

    // @Query(value="select sum(montant) from achat where id")

}
