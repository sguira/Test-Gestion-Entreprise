package com.gestion_entreprise.gestion_entreprise.entities;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class Tache {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    protected Long id;
    String description;
    String date_;
    String heure;
}
