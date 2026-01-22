package com.app.auth;


import com.app.entity.Utilisateur;

public interface AuthenticationService {

    Utilisateur authenticate(String username, String password);

    void logout();
}

