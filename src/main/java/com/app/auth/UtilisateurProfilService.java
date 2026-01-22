package com.app.auth;

public interface UtilisateurProfilService {

    void affecterProfil(Long utilisateurId, Long profilId);

    void retirerProfil(Long utilisateurId, Long profilId);
}
