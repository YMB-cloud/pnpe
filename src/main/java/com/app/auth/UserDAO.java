package com.app.auth;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.Utilisateur;

@Stateless
public class UserDAO {

    @PersistenceContext
    private EntityManager em;

    public Utilisateur findByUsername(String username) {
        return em.createQuery(
            "SELECT u FROM User u WHERE u.username = :username",
            Utilisateur.class
        )
        .setParameter("username", username)
        .getResultStream()
        .findFirst()
        .orElse(null);
    }
}

