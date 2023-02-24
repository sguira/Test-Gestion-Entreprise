package com.gestion_entreprise.gestion_entreprise.repositorie;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import com.gestion_entreprise.gestion_entreprise.entities.Users;

@RestResource
public interface UserRepositori extends JpaRepository<Users, Long> {

    @Query(value = "SELECT sum(A.montant) from achat A,users U, users_achats U_C where U_C.users_id=1 and A.id=U_C.achats_id and A.date between :#{#date1} and :#{#date2} and U.id=1 ", nativeQuery = true)
    Double get_bilan_achat_montant(@Param("date2") String date2, @Param("date1") String date1);

    @Query(value = "SELECT sum(A.payee) from ventes A,users U, users_ventes U_C where U_C.users_id=1 and A.id=U_C.ventes_id and A.date between :#{#date1} and :#{#date2} and U.id=1 ", nativeQuery = true)
    Double get_bilan_ventes_montant(@Param("date2") String date2, @Param("date1") String date1);

    @Query(value = "SELECT sum(A.paye) from achat A,users U, users_achats U_C where U_C.users_id=1 and A.id=U_C.achats_id and A.date between :#{#date1} and :#{#date2} and U.id=1 ", nativeQuery = true)
    Double get_bilan_achat_paye(@Param("date2") String date2, @Param("date1") String date1);

    @Query(value = "SELECT sum(A.payee) from ventes A,users U, users_ventes U_C where U_C.users_id=1 and A.id=U_C.ventes_id and A.date between :#{#date1} and :#{#date2} and U.id=1 ", nativeQuery = true)
    Double get_bilan_ventes_paye(@Param("date2") String date2, @Param("date1") String date1);

    @Query(value = "SELECT sum(V.payee) from ventes V,users U,users_ventes U_V where U_V.users_id = :#{#id} and V.id=U_V.ventes_id and V.date=:#{#date_} ", nativeQuery = true)
    Double get_ventes_journaliere(@Param("date_") String date, @Param("id") Long id);

    @Query(value = " SELECT DAYOFWEEK(now()); ", nativeQuery = true)
    List ventes_de_la_semaine();

    @Query(value = "SELECT date,code,prix FROM ventes V,users_ventes U_V, WHERE V.id=U_V.ventes_id , U_V.users_id = :#{#id} ", nativeQuery = true)
    List<?> all_ventes(@Param("id") Long id);
}
