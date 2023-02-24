package com.gestion_entreprise.gestion_entreprise.repositorie;

import java.util.List;

import javax.websocket.server.PathParam;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.rest.core.annotation.RestResource;

import com.gestion_entreprise.gestion_entreprise.entities.Client;

@RestResource
public interface ClientRepo extends JpaRepository<Client, Long> {

    @Query(value = "SELECT c.name c from client c Where c.name like '%{#name}%'", nativeQuery = true)
    List<Client> search_by_name(@PathParam("id") String id, @PathParam("name") String name);

}
