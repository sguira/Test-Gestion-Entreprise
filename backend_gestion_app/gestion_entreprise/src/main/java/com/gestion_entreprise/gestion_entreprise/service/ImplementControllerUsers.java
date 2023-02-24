package com.gestion_entreprise.gestion_entreprise.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.gestion_entreprise.gestion_entreprise.entities.Users;
import com.gestion_entreprise.gestion_entreprise.inserface.UserControllerInterface;
import com.gestion_entreprise.gestion_entreprise.repositorie.UserRepositori;

@Service
public class ImplementControllerUsers implements UserControllerInterface {

    @Autowired(required = true)
    UserRepositori users;

    @Override
    public List<Users> getAllUsers() {
        List<Users> u = users.findAll();
        return u;
    }

    @Override
    public Users getUserByEmail(Long id) {

        return users.findById(id).get();
    }

    @Override
    public void addUser(@RequestBody Users u) {
        users.save(u);
    }

    @Override
    public int deleteUser(Long id) {
        try {
            users.deleteById(id);
            return 0;
        } catch (Exception e) {
            return 1;
        }

    }

    @Override
    public Users updateUser(Users u) {
        users.save(u);
        return u;
    }

}
