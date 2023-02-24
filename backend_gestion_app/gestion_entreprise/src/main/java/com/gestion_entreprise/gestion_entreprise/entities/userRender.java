package com.gestion_entreprise.gestion_entreprise.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class userRender {

    public Long id;
    public String name;
    public String email;
    public String number;
    public InfoEntreprise info;

}
