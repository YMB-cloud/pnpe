package com.app.auth;

import java.util.List;

import com.app.entity.Site;
import com.app.entity.Utilisateur;
import com.app.entity.UtilisateurDTO;

public interface UtilisateurService {

    Utilisateur creer(UtilisateurDTO dto,List<String>  selectedProfileIds);

    Utilisateur modifier(UtilisateurDTO dto,List<String> selectedProfileIds);

    void desactiver(Long utilisateurId);

    Utilisateur trouverParId(Long id);

    List<Utilisateur> lister();
    
    Utilisateur findByUsername(String username);
    
    void updatePassword(Long userId, String hashedPassword) ;
    
    String resetPassword(Long userId, Utilisateur admin);
    
}

