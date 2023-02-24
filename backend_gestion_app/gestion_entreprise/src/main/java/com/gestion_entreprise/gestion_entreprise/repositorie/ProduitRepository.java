package com.gestion_entreprise.gestion_entreprise.repositorie;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.gestion_entreprise.gestion_entreprise.entities.Produit;

@RestResource
public interface ProduitRepository extends JpaRepository<Produit, Long> {

    @Query(value = "Select p from produit, where p.name Like '%:#{#mc%'", nativeQuery = true)
    public List<Produit> findByNameContains(@Param("mc") String ck, @Param("id") Long id);

}
