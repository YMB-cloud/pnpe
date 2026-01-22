package com.app.auth;


import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import org.mindrot.jbcrypt.BCrypt;

import com.app.entity.Utilisateur;

@RequestScoped
public class AuthenticationServiceImpl implements AuthenticationService {

    @Inject
    private UtilisateurService utilisateurService;

    @Inject
    private SecurityContext securityContext;

    @Inject
    private AuditService auditService;

    @Override
    public Utilisateur authenticate(String username, String password) {

        Utilisateur user = utilisateurService.findByUsername(username);

        if (user == null || !Boolean.TRUE.equals(user.getActive())) {
            //auditService.log("LOGIN_FAILED",securityContext.getUtilisateur(), "Identifiant incorrect");
        	System.out.println("LOGIN_FAILED BAD USERNAME");
            return null;
        }

        if (!BCrypt.checkpw(password, user.getPassword())) {
            auditService.log("LOGIN_FAILED",user, "Mot de passe incorrect");
            System.out.println("LOGIN_FAILED BAD PASSWORD");
            return null;
        }

        // Initialisation du contexte de sécurité
        securityContext.init(user);
        //System.out.println("LOGIN_SUCCESS");
        auditService.log("LOGIN_SUCCESS",user, username);

        return user;
    }

    @Override
    public void logout() {
        auditService.log("LOGOUT",securityContext.getUtilisateur(), securityContext.getUtilisateur().getUsername());
        securityContext.clear();
    }
}

