package com.app.auth;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.Profile;
import com.app.entity.Utilisateur;

@Stateless
public class UtilisateurProfilServiceImpl implements UtilisateurProfilService {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private AuditService auditService;
    
    @Inject
    private SecurityContext securityContext;

    @Override
    public void affecterProfil(Long utilisateurId, Long profilId) {

        Utilisateur utilisateur = em.find(Utilisateur.class, utilisateurId);
        Profile profil = em.find(Profile.class, profilId);

        if (utilisateur == null || profil == null) {
            throw new IllegalArgumentException("Utilisateur ou profil introuvable");
        }

        // 🔐 Sécurité multi-site
       /* if (!utilisateur.getSite().getId().equals(profil.getSite().getId())) {
            throw new SecurityException("Profil et utilisateur de sites différents");
        }*/

        // ⚠️ éviter doublon
        if (!utilisateur.getProfiles().contains(profil)) {
        	
            // 🔍 AUDIT MÉTIER
            auditService.log(
            	"AFFECTATION_PROFILE", securityContext.getUtilisateur(),"Profile affecté à "+utilisateur.getUsername()+" : " + profil.getLibelle()
            );
        	
            utilisateur.getProfiles().add(profil);
        }
    }

    @Override
    public void retirerProfil(Long utilisateurId, Long profilId) {

        Utilisateur utilisateur = em.find(Utilisateur.class, utilisateurId);
        Profile profil = em.find(Profile.class, profilId);

        if (utilisateur == null || profil == null) {
            return;
        }

        // 🔍 AUDIT MÉTIER
        auditService.log(
        	"RETRAIT_PROFILE", securityContext.getUtilisateur(),"Profile retiré à "+utilisateur.getUsername()+" : " + profil.getLibelle()
        );
        utilisateur.getProfiles().remove(profil);
    }
    

}

