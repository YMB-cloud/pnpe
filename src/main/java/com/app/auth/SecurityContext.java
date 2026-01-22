package com.app.auth;

import java.util.Set;

import com.app.entity.Profile;
import com.app.entity.Role;
import com.app.entity.Site;
import com.app.entity.Utilisateur;

public interface SecurityContext {

    Utilisateur getUtilisateur();
    Site getSite();

    Set<Profile> getProfils();
    Set<Role> getRoles();

    boolean isAuthenticated();
    boolean hasRole(String codeRole);

    void init(Utilisateur utilisateur);
    void clear();
    
    Utilisateur getCurrentUser() ;
}
