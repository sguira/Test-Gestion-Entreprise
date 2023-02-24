package com.gestion_entreprise.gestion_entreprise.inserface;

import java.util.List;

import com.gestion_entreprise.gestion_entreprise.entities.Users;

public interface UserControllerInterface {

    public List<Users> getAllUsers();

    public Users getUserByEmail(Long id);

    public void addUser(Users u);

    public int deleteUser(Long id);

    public Users updateUser(Users u);

}
