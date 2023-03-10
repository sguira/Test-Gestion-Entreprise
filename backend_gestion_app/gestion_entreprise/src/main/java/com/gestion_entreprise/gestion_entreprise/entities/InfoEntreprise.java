package com.gestion_entreprise.gestion_entreprise.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Data
public class InfoEntreprise implements Serializable {

    String name;
    String telephone;
    String addresse;
    String logo;
}
