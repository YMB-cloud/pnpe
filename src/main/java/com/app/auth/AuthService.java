package com.app.auth;

import javax.ejb.Stateless;
import javax.inject.Inject;

import com.app.entity.Utilisateur;

@Stateless
public class AuthService {

    @Inject
    private UserDAO userDAO;

    public Utilisateur authenticate(String username, String password) {

    	Utilisateur user = userDAO.findByUsername(username);

        if (user == null || !user.getActive()) {
            return null;
        }

        if (PasswordUtils.verify(password, user.getPassword())) {
            return user;
        }

        return null;
    }
}

