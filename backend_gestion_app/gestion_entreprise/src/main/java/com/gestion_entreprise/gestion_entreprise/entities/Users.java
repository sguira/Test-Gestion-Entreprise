package com.gestion_entreprise.gestion_entreprise.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import lombok.ToString;

@Entity

@AllArgsConstructor
@ToString
@NoArgsConstructor
@Data
public class Users implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String last_name;
    private String number;

    private String email;
    private String password;
    private String image_url;
    private InfoEntreprise info;

    // paramètre global

    String lang = "fr";

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Categorie> categories = new ArrayList<>();

    @JsonIgnoreProperties("costMaterials")
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Client> clients = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Collection<Contact> contacts = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Fournisseur> fournisseurs = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Achat> achats = new ArrayList<>();

    // @JsonProperty(access = Access.WRITE_ONLY)
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ventes> ventes = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY)
    private List<Tache> taches = new ArrayList();

    public void ajouterCategorie(Categorie cat) {
        this.categories.add(cat);
    }

    public void ajouter_client(Client u) {
        this.clients.add(u);
    }

    public void ajouter_contact(Contact c) {
        this.contacts.add(c);
    }

    public void ajouter_fournisseur(Fournisseur f) {
        this.fournisseurs.add(f);
    }

    public void ajouter_ventes(ventes v) {
        this.ventes.add(v);
    }

    public void ajouter_achats(Achat a) {
        this.achats.add(a);
    }

    public void ajouter_tache(Tache t) {
        this.taches.add(t);
    }

}
