package com.gestion_entreprise.gestion_entreprise.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.gestion_entreprise.gestion_entreprise.entities.Achat;
import com.gestion_entreprise.gestion_entreprise.entities.Bilan;
import com.gestion_entreprise.gestion_entreprise.entities.Categorie;
import com.gestion_entreprise.gestion_entreprise.entities.Client;
import com.gestion_entreprise.gestion_entreprise.entities.Contact;
import com.gestion_entreprise.gestion_entreprise.entities.Fournisseur;
import com.gestion_entreprise.gestion_entreprise.entities.Image;
import com.gestion_entreprise.gestion_entreprise.entities.InfoEntreprise;
import com.gestion_entreprise.gestion_entreprise.entities.Produit;
import com.gestion_entreprise.gestion_entreprise.entities.Tache;
import com.gestion_entreprise.gestion_entreprise.entities.Users;
import com.gestion_entreprise.gestion_entreprise.entities.userRender;
import com.gestion_entreprise.gestion_entreprise.entities.ventes;
import com.gestion_entreprise.gestion_entreprise.repositorie.AchatR;
import com.gestion_entreprise.gestion_entreprise.repositorie.CategorieRepositori;
import com.gestion_entreprise.gestion_entreprise.repositorie.ClientRepo;
import com.gestion_entreprise.gestion_entreprise.repositorie.ContactRepo;
import com.gestion_entreprise.gestion_entreprise.repositorie.FournisseurRepo;
import com.gestion_entreprise.gestion_entreprise.repositorie.ImageRepository;
import com.gestion_entreprise.gestion_entreprise.repositorie.ProduitRepository;
import com.gestion_entreprise.gestion_entreprise.repositorie.TacheRepositorie;
import com.gestion_entreprise.gestion_entreprise.repositorie.UserRepositori;
import com.gestion_entreprise.gestion_entreprise.repositorie.VenteRepo;

@CrossOrigin("*")
@RestController
@RequestMapping(path = "/backend")
public class controller {

    @Autowired(required = true)
    UserRepositori usersR;

    @Autowired(required = true)
    CategorieRepositori catR;

    @Autowired(required = true)
    ProduitRepository pr;

    @Autowired(required = true)
    ClientRepo clientR;

    @Autowired(required = true)
    ContactRepo contactR;

    @Autowired(required = true)
    FournisseurRepo fournisseurRepo;

    @Autowired(required = true)
    VenteRepo venteRepo;

    @Autowired(required = true)
    AchatR achatR;

    @Autowired(required = true)
    TacheRepositorie tacheR;

    @Autowired(required = true)
    ImageRepository imageRe;

    @PostMapping(path = "/adduser")
    ResponseEntity<Users> ajouterUtilisateur(@RequestBody Users u) {

        if (!verifier.addUsers(usersR.findAll(), u.getEmail())) {

            return new ResponseEntity<>(usersR.save(u), HttpStatus.CREATED);
        }

        return new ResponseEntity<Users>(HttpStatus.CONFLICT);
    }

    @PostMapping(path = "/ajouter_user")
    ResponseEntity<Users> ajouter_utilisateur2(
            @RequestParam(name = "name") String name,
            @RequestParam(name = "email") String email,
            @RequestParam(name = "password") String password,
            @RequestParam(name = "number") String number,
            @RequestParam(name = "image") MultipartFile image,
            @RequestParam(name = "entreprise") String n_en,
            @RequestParam(name = "addresse") String addrese,
            @RequestParam(name = "num") String num) {

        Users users = new Users();
        users.setName(name);
        users.setEmail(email);
        users.setNumber(number);
        users.setPassword(password);
        users.setImage_url(image.getOriginalFilename());
        InfoEntreprise info = new InfoEntreprise(n_en, num, addrese, image.getOriginalFilename());
        System.out.println("info info:" + info.getName());
        users.setInfo(info);
        try {
            File ul = new File("D://GESTION CLIENT APP//images//image_profit//",
                    image.getOriginalFilename());
            ul.createNewFile();
            FileOutputStream fout = new FileOutputStream(ul);
            fout.write(image.getBytes());
            fout.close();
        } catch (Exception e) {
            System.out.print("mon erreru" + e.toString());
        } finally {
            if (!verifier.addUsers(usersR.findAll(), email)) {

                return new ResponseEntity<>(usersR.save(users), HttpStatus.CREATED);
            }

            return new ResponseEntity<Users>(HttpStatus.CONFLICT);
        }

    }

    @GetMapping(path = "users")
    ResponseEntity<List<Users>> alluser() {
        return new ResponseEntity<List<Users>>(usersR.findAll(), HttpStatus.OK);
    }

    @PostMapping(path = "/addcategorie/{id}")
    ResponseEntity<Categorie> ajouterCategorie(@RequestBody Categorie cat, @PathVariable(name = "id") Long id) {
        Users u = usersR.findById(id).get();
        List<Categorie> cats = (List<Categorie>) (u.getCategories());
        if (cats.size() > 0) {
            for (int i = 0; i < cats.size(); i++) {
                if (cats.get(i).getName() != null) {
                    if (cats.get(i).getName().equals(cat.getName())) {
                        System.out.println(" CONFLICT >>>>>>>>>>>>> ");
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }
                }
            }
        }
        u.ajouterCategorie(cat);
        catR.save(cat);
        return new ResponseEntity<Categorie>(catR.save(cat), HttpStatus.CREATED);

    }

    @GetMapping(path = "/get_user_by_id/{id}")
    ResponseEntity<userRender> get_users(@PathVariable(name = "id") Long id) {

        Users u = usersR.findById(id).get();
        userRender u_ = new userRender(u.getId(), u.getName(), u.getEmail(), u.getNumber(), u.getInfo());

        return new ResponseEntity<userRender>(u_, HttpStatus.OK);
    }

    @PostMapping(path = "/login")
    int login(@RequestBody Users u) {
        List<Users> result = usersR.findAll();
        for (int i = 0; i < result.size(); i++) {
            if (result.get(i).getEmail().equals(u.getEmail()) &&
                    result.get(i).getPassword().equals(u.getPassword())) {
                return (int) result.get(i).getId().intValue();
            }
        }
        return -1;
    }

    @PostMapping(path = "/addproduit/{id}")
    ResponseEntity<Produit> ajouterProduit(@RequestBody Produit p,
            @PathVariable(name = "id") Long id,
            @RequestPart MultipartFile img) throws IOException {
        Categorie cat = catR.findById(id).get();
        String img_name = img.getOriginalFilename();
        p.setImage_url(img_name);
        cat.ajouterProduit(p);
        catR.save(cat);
        File f = new File("images_gestion_entreprise/", img_name);
        FileOutputStream fout = new FileOutputStream(f);
        fout.write(img.getBytes());
        fout.close();
        return new ResponseEntity<>(pr.save(p), HttpStatus.CREATED);

    }

    @PostMapping(path = "/ajouter_produit/{id}")
    ResponseEntity<Produit> ajouter_produitf(@RequestBody Produit p, @PathVariable Long id) {
        Categorie cat = catR.findById(id).get();
        cat.ajouterProduit(p);
        catR.save(cat);
        return new ResponseEntity<Produit>(pr.save(p), HttpStatus.CREATED);
    }

    @GetMapping(path = "/get_clients/{id}")
    ResponseEntity<List<Client>> clients(@PathVariable Long id) {

        return new ResponseEntity<List<Client>>((List<Client>) usersR.findById(id).get().getClients(), HttpStatus.OK);

    }

    @PutMapping(path = "/update_user")
    ResponseEntity<Users> modifierUtilisateur(@RequestBody Users u) {

        Users user = usersR.findById(u.getId()).get();
        user.setName(u.getName());
        user.setEmail(u.getEmail());
        user.setLast_name(u.getLast_name());
        user.setNumber(u.getNumber());
        user.setPassword(u.getPassword());
        return new ResponseEntity<Users>(usersR.save(user), HttpStatus.CREATED);

    }

    @PostMapping(path = "/update_user/{id}")
    ResponseEntity<Users> modifierUser(@RequestBody Users u, @PathVariable Long id) {
        Users u_ = usersR.findById(id).get();

        System.out.println("appel..\n");
        u_.setName(u.getName());
        u_.setEmail(u.getEmail());
        u_.setNumber(u.getNumber());
        u_.setInfo(u.getInfo());
        return new ResponseEntity<>(usersR.save(u_), HttpStatus.CREATED);
    }

    @GetMapping(path = "/get_user/{id}")
    ResponseEntity<Users> getUser(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<Users>(usersR.findById(id).get(), HttpStatus.OK);
    }

    @PostMapping(path = "/add_client/{id}")
    ResponseEntity<Client> ajouter_client(@RequestBody Client c, @PathVariable(name = "id") Long id) {
        Users u = usersR.findById(id).get();
        List<Client> clients = new ArrayList<Client>();
        clients = u.getClients();

        u.ajouter_client(c);
        Contact contact = new Contact();
        contact.setName(c.getName());
        contact.setNumero(c.getNumber());
        contactR.save(contact);
        u.ajouter_contact(contact);
        usersR.save(u);
        return new ResponseEntity<Client>(clientR.save(c), HttpStatus.CREATED);
    }

    @PostMapping(path = "/add_contact/{id}")
    ResponseEntity<Contact> ajouterContact(@RequestBody Contact c, @PathVariable Long id) {
        Users u = usersR.findById(id).get();
        u.ajouter_contact(c);
        usersR.save(u);
        return new ResponseEntity<Contact>(contactR.save(c), HttpStatus.CREATED);
    }

    @GetMapping(path = "/all_categories/{id}")
    ResponseEntity<List<Categorie>> get_categorie_by_user(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<List<Categorie>>((List<Categorie>) usersR.findById(id).get().getCategories(),
                HttpStatus.OK);
    }

    @PostMapping(path = "/add_article/{id}")
    ResponseEntity<Produit> ajouter_article(@RequestBody Produit p, @PathVariable(name = "id") Long id) {
        Categorie cat = catR.findById(id).get();
        cat.ajouterProduit(p);
        catR.save(cat);
        return new ResponseEntity<Produit>(pr.save(p), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update_article/{id}/{id_}")
    ResponseEntity<Produit> modifier_article(@RequestBody Produit p, @PathVariable(name = "id") Long id,
            @PathVariable(name = "id_") Long id_) {
        Users u = usersR.findById(id).get();
        Categorie cat = catR.findById(id_).get();

        List<Produit> prod = cat.getProduits();
        for (var i = 0; i < prod.size(); i++) {
            if (prod.get(i).getId() == p.getId()) {
                System.out.println("okok\n\n");
                prod.get(i).setName(p.getName());
                prod.get(i).setPrix(p.getPrix());
                prod.get(i).setDescription(p.getDescription());
            }
        }

        catR.save(cat);
        return new ResponseEntity<Produit>(pr.save(p), HttpStatus.ACCEPTED);
    }

    @GetMapping(path = "/all_contact/{id}")
    ResponseEntity<List<Contact>> contacts(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<List<Contact>>((List<Contact>) usersR.findById(id).get().getContacts(),
                HttpStatus.OK);
    }

    @PostMapping(path = "/add_fournisseur/{id}")
    ResponseEntity<Fournisseur> add_fournisseur(@RequestBody Fournisseur f, @PathVariable Long id) {
        Users u = usersR.findById(id).get();
        u.ajouter_fournisseur(f);
        usersR.save(u);
        return new ResponseEntity<Fournisseur>(fournisseurRepo.save(f), HttpStatus.CREATED);
    }

    @PostMapping(path = "/add_ventes/{id_user}/{id_clients}")
    ResponseEntity<?> ajouter_ventes(@RequestBody ventes v, @PathVariable(name = "id_user") Long id_1,
            @PathVariable(name = "id_clients") Long id_2) {
        Users u = usersR.findById(id_1).get();
        if (id_2 != -1) {

            ventes v2 = venteRepo.save(v);

            Client c = clientR.findById(id_2).get();
            c.ajouter_ventes(v2);
            u.ajouter_ventes(v2);
            clientR.save(c);
            usersR.save(u);
            return new ResponseEntity(HttpStatus.CREATED);
        } else {
            u.ajouter_ventes(v);
            usersR.save(u);
            return new ResponseEntity(HttpStatus.CREATED);
        }

    }

    @DeleteMapping(path = "/delete_categorie/{id}")
    int deletecategorie(@PathVariable Long id) throws Exception {
        try {
            catR.deleteById(id);
            ;
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    @PostMapping(path = "/save_produit/{id_2}")
    ResponseEntity<?> save_article(@PathVariable(name = "id_2") Long id,
            @RequestParam(name = "image") MultipartFile image,
            @RequestParam(name = "name") String nom,
            @RequestParam(name = "description") String description, @RequestParam(name = "prix") String prix,
            @RequestParam(name = "quantite") String quantite) {
        Produit p = new Produit();

        // create the product
        p.setDescription(description);
        p.setName(nom);
        p.setPrix(Double.parseDouble(prix));
        p.setQuantite(Integer.parseInt(quantite));
        p.setImage_url(image.getOriginalFilename());
        try {
            File ul = new File("D://GESTION CLIENT APP//images//images_article//",
                    image.getOriginalFilename());
            ul.createNewFile();
            FileOutputStream fout = new FileOutputStream(ul);
            fout.write(image.getBytes());
            fout.close();
        } catch (Exception e) {
            System.out.print("mon erreru" + e.toString());
        }
        Categorie cat = catR.findById(id).get();
        cat.ajouterProduit(p);
        catR.save(cat);
        return new ResponseEntity<>(pr.save(p), HttpStatus.CREATED);
    }

    @PostMapping(value = "/ajouter_produit2/{id}")
    ResponseEntity<?> save_produit2(@PathVariable(name = "id") Long id, @RequestBody Produit p) {
        Categorie cat = catR.findById(id).get();
        cat.ajouterProduit(p);
        catR.save(cat);

        return new ResponseEntity<>(pr.save(p), HttpStatus.CREATED);
    }

    @GetMapping(path = "/get_client/{id}")
    ResponseEntity<List<ventes>> get_clients_ventes(@PathVariable(name = "id") Long id) {

        return new ResponseEntity<List<ventes>>((List<ventes>) clientR.findById(id).get().getVentes(), HttpStatus.OK);
    }

    @GetMapping(path = "/ventes_for_clients/{id}/{choice}")
    List<ventes> getVentes(@PathVariable Long id, @PathVariable(name = "choice") Long choice) {
        return clientR.findById(id).get().getVentes();
    }

    @GetMapping(path = "/get_fournisseur/{id}")
    ResponseEntity<List<Fournisseur>> fournisseurs(@PathVariable Long id) {
        return new ResponseEntity<List<Fournisseur>>((List<Fournisseur>) usersR.findById(id).get().getFournisseurs(),
                HttpStatus.OK);
    }

    @GetMapping(path = "/get_bilan/{date1}/{date2}")
    ResponseEntity<Bilan> bilan_ventes(@PathVariable(name = "date2") String date2,
            @PathVariable(name = "date1") String date1) {
        Bilan bilan = new Bilan();

        bilan.setMontant_achat(usersR.get_bilan_achat_montant(date2, date1));
        bilan.setMontant_vente(usersR.get_bilan_ventes_montant(date2, date1));
        bilan.setPaye_achat(usersR.get_bilan_achat_paye(date2, date1));
        bilan.setPaye_vente(usersR.get_bilan_ventes_paye(date2, date1));

        return new ResponseEntity<Bilan>(bilan, HttpStatus.OK);
    }

    @PostMapping(path = "/add_achat/{id_1}/{id_2}")
    ResponseEntity<Achat> ajouter_depense(@PathVariable(name = "id_1") Long id, @PathVariable(name = "id_2") Long id_,
            @RequestBody Achat a) {
        if (id_ != -1) {
            Users u = usersR.findById(id).get();
            u.ajouter_achats(a);
            usersR.save(u);
            Fournisseur f = fournisseurRepo.findById(id_).get();
            f.ajouter_achat(a);
            fournisseurRepo.save(f);
            return new ResponseEntity<Achat>(achatR.save(a), HttpStatus.CREATED);
        } else {
            Users u = usersR.findById(id).get();
            u.ajouter_achats(a);
            usersR.save(u);
            return new ResponseEntity<Achat>(achatR.save(a), HttpStatus.CREATED);
        }
    }

    // supprimer un produit
    @DeleteMapping(path = "/delete_product/{id}")
    int effacer_produit(@PathVariable Long id) {
        try {
            pr.deleteById(id);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    // delete
    @DeleteMapping(path = "/delete/{id}/{parent_id}/{choice}")
    int delete(@PathVariable(name = "id") Long id, @PathVariable(name = "parent_id") Long parent,
            @PathVariable(name = "choice") int choice) {
        switch (choice) {
            case 1: {
                Produit p = pr.findById(id).get();
                System.out.println("\n\n id cat:" + parent);
                Categorie cat = catR.findById(parent).get();
                // cat.effacer_produit(p);
                for (var i = 0; i < cat.getProduits().size(); i++) {
                    if (cat.getProduits().get(i).getId() == p.getId()) {
                        System.out.println("produit trouvé \n\n");
                        cat.getProduits().remove(i);
                    }
                }
                catR.save(cat);
                pr.deleteById(id);
                return 1;
            }
            case 2: {
                pr.deleteById(id);
                return 1;
            }
        }
        return -1;
    }

    @GetMapping(path = "/get_ventes_journaliere/{id}/{choice}/{date1}/{date2}/{date3}/{date4}/{date5}/{date6}/{date7}")
    List<Double> ventes_journaliere(@PathVariable(name = "id") Long id,
            @PathVariable(name = "date1") String date1,
            @PathVariable(name = "choice") int choice,
            @PathVariable(name = "date2") String date2,
            @PathVariable(name = "date3") String date3,
            @PathVariable(name = "date4") String date4,
            @PathVariable(name = "date5") String date5,
            @PathVariable(name = "date6") String date6,
            @PathVariable(name = "date7") String date7) {

        List<Double> data = new ArrayList<>();
        switch (choice) {
            case 1: {
                List<ventes> v = usersR.findById(id).get().getVentes();

                data.add(get_ventes_total(v, date1));

                return data;
            }
            case 2: {
                List<ventes> v = usersR.findById(id).get().getVentes();
                data.add(
                        get_ventes_total(v, date7));
                data.add(
                        get_ventes_total(v, date6));
                data.add(
                        get_ventes_total(v, date5));
                data.add(
                        get_ventes_total(v, date4));
                data.add(
                        get_ventes_total(v, date3));
                data.add(
                        get_ventes_total(v, date2));
                data.add(
                        get_ventes_total(v, date1));
                return data;
            }
            case 3: {
                List<Achat> v = usersR.findById(id).get().getAchats();
                data.add(get_achats_total(v, date7));
                data.add(
                        get_achats_total(v, date6));
                data.add(
                        get_achats_total(v, date5));
                data.add(get_achats_total(v, date4));
                data.add(
                        get_achats_total(v, date3));
                data.add(
                        get_achats_total(v, date2));
                data.add(
                        get_achats_total(v, date1));
                return data;
            }
            default: {
                return new ArrayList<>();
            }
        }
    }

    double get_ventes_total(List<ventes> v, String date) {
        double res = 0d;
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i).getDate().toString().equals(date.toString())) {
                res += v.get(i).getPayee();
            }
        }
        return res;
    }

    @GetMapping(path = "/search_client_by_name/{id}/{name}")
    List<Client> recherche_client(@PathVariable(name = "id") String id, @PathVariable(name = "name") String name) {
        return clientR.search_by_name(id, name);
    }

    // Fonction pour modifier un client
    @PutMapping(path = "/update_client/{id}")
    ResponseEntity<Client> update_client(@PathVariable(name = "id") Long id, @RequestBody Client c) {
        Users u = usersR.findById(id).get();

        List<Client> clients = u.getClients();
        for (int i = 0; i < clients.size(); i++) {
            if (clients.get(i).getId() == c.getId()) {
                clients.get(i).setEmail(c.getEmail());
                clients.get(i).setName(c.getName());
                clients.get(i).setNumber(c.getNumber());
                break;
            }
        }
        usersR.save(u);
        return new ResponseEntity<Client>(clientR.save(c), HttpStatus.OK);
    }

    // Fonction pour modifier un client
    @PutMapping(path = "/update_fournisseur/{id}")
    ResponseEntity<Fournisseur> update_fournisseur(@PathVariable(name = "id") Long id, @RequestBody Fournisseur c) {
        Users u = usersR.findById(id).get();
        System.out.println("Le client recu:>>>");
        System.out.println("id:" + c.getId());
        System.out.println("Name:" + c.getName());
        System.out.println("Email:" + c.getEmail());
        System.out.println("Number:" + c.getNumber());
        System.out.println(c.getId() + " le id >>>>>>>>>>>>>>>\n\n\n");
        List<Fournisseur> f = u.getFournisseurs();
        for (int i = 0; i < f.size(); i++) {
            if (f.get(i).getId() == c.getId()) {
                f.get(i).setEmail(c.getEmail());
                f.get(i).setName(c.getName());
                f.get(i).setNumber(c.getNumber());
                break;
            }
        }
        usersR.save(u);
        return new ResponseEntity<Fournisseur>(fournisseurRepo.save(c), HttpStatus.OK);
    }

    // ventes de la semaine
    @GetMapping("/vente_de_la_semaine")
    List<?> vente_semaine() {
        return usersR.ventes_de_la_semaine();
    }

    @GetMapping(path = "/all_ventes/{id}")
    List<?> all_ventes(@PathVariable(name = "id") Long id) {
        List<?> v = new ArrayList<>();
        v = usersR.all_ventes(id);
        return v;

    }

    @GetMapping(path = "ventes/{id}")
    ResponseEntity<List<ventes>> find_ventes(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<List<ventes>>(usersR.findById(id).get().getVentes(), HttpStatus.OK);
    }

    @GetMapping(path = "achats/{id}")
    ResponseEntity<List<Achat>> find_achats(@PathVariable(name = "id") Long id) {
        return new ResponseEntity<List<Achat>>(usersR.findById(id).get().getAchats(), HttpStatus.OK);
    }

    @PutMapping(path = "/client_modifier/{id}")
    ResponseEntity<Client> modifier(Long id, Client client) {
        // List<Client> clients=usersR.findById(id).get().getClients();
        // clients.forEach((c)->{
        // if(c.getId()==client.getId()){
        // c=client;
        // }
        // });
        // usersR.save(null);

        return new ResponseEntity<>(clientR.save(client), HttpStatus.CREATED);
    }

    @PostMapping(path = "/add_tache/{id}")
    ResponseEntity<?> add_tache(@PathVariable(name = "id") Long id, @RequestBody Tache t) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date d = new Date();
        Tache tr = t;
        tr.setDate_(format.format(d).toString());
        System.out.println(format.format(d));
        Users u = usersR.findById(id).get();
        u.ajouter_tache(tacheR.save(tr));
        usersR.save(u);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(path = "/find_days/{id}")
    List<Tache> find_days(@PathVariable Long id, String date) {

        return usersR.findById(id).get().getTaches();
    }

    @GetMapping(path = "/ventes_semaine/{id}/{date}/{jour_semaine}/{choice}")
    public List<Double> vente2(@PathVariable(name = "id") Long id, @PathVariable(name = "date") String param,
            @PathVariable(name = "jour_semaine") int jj, @PathVariable int choice) {
        String[] date = param.split(",");

        List<ventes> v = usersR.findById(id).get().getVentes();
        List<Achat> a = usersR.findById(id).get().getAchats();
        List<Double> data = new ArrayList<>();
        int i = 0;
        int j = jj;

        switch (choice) {
            case 1: {
                while (j >= 1) {
                    System.out.println("ligne:" + i);
                    try {
                        data.add(get_ventes_total(v, date[i]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                    j--;
                }
                break;
            }
            case 2: {

                while (j >= 1) {
                    System.out.println("ligne:" + i);
                    try {
                        data.add(get_achats_total(a, date[i]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    i++;
                    j--;
                }
                break;

            }
        }

        while (jj < 7) {
            data.add(0d);
            jj++;
        }

        return data;

    }

    double get_achats_total(List<Achat> v, String date) {
        double res = 0d;
        for (int i = 0; i < v.size(); i++) {
            if (v.get(i).getDate().toString().equals(date.toString())) {
                res += v.get(i).getPaye();
            }
        }
        return res;
    }
}
