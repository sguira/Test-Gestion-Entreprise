package com.gestion_entreprise.gestion_entreprise.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bilan implements Serializable {

    public Double montant_vente;
    public Double paye_vente;
    public Double montant_achat;
    public Double paye_achat;

}
