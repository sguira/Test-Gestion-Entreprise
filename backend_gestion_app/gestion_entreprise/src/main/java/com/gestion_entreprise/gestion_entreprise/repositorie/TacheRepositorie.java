package com.gestion_entreprise.gestion_entreprise.repositorie;

import java.sql.SQLException;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.gestion_entreprise.gestion_entreprise.entities.Tache;

@RestResource
public interface TacheRepositorie extends JpaRepository<Tache, Long> {

    @Query(nativeQuery = true, value = " SELECT * from tache t,users_taches u WHERE u.users_id=:#{#id},t.date=:#{#date}")
    List<Tache> findTacheNow(@Param("date") String date, @Param("id") Long id) throws SQLException;

}
