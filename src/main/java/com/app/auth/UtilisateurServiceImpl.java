package com.app.auth;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import org.mindrot.jbcrypt.BCrypt;

import com.app.entity.Site;
import com.app.entity.Utilisateur;
import com.app.entity.UtilisateurDTO;

@Stateless
public class UtilisateurServiceImpl implements UtilisateurService {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private ProfilService profilService;
    
    @Inject
    private AuditService auditService;
    
    @Inject
    private SecurityContext securityContext;
    
    @Override
    public Utilisateur findByUsername(String username) {
        try {
            return em.createQuery(
                    "SELECT u FROM Utilisateur u WHERE u.username = :username",
                    Utilisateur.class)
                .setParameter("username", username)
                .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public Utilisateur creer(UtilisateurDTO dto,List<String> selectedProfileIds) {

        // 🔐 Vérifier unicité username par site
        Long count = em.createQuery(
            "SELECT COUNT(u) FROM Utilisateur u " +
            "WHERE u.username = :username ",
            Long.class)
            .setParameter("username", dto.getUsername())
            .getSingleResult();

        if (count > 0) {
            throw new IllegalStateException("Nom d'utilisateur déjà utilisé");
        }

        Site site = em.find(Site.class, dto.getSiteId());
        if (site == null) {
            throw new IllegalArgumentException("Site introuvable");
        }

        Utilisateur u = new Utilisateur();
        u.setUsername(dto.getUsername());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setActive(true);
        u.setSite(site);

        // 🔐 Hash mot de passe
        u.setPassword(BCrypt.hashpw("00000000", BCrypt.gensalt()));
        
        
        u.getProfiles().clear();
        for (String id : selectedProfileIds) {
            u.getProfiles().add(profilService.findById(Long.valueOf(id)));
        }

        em.persist(u);
        
        
        // 🔍 AUDIT MÉTIER
        auditService.log(
        	"CREATION_UTILISATEUR", securityContext.getUtilisateur(),"Utilisateur créé : " + dto.getUsername()
        );
        return u;
    }

    @Override
    public Utilisateur modifier(UtilisateurDTO dto,List<String> selectedProfileIds) {

        Utilisateur u = em.find(Utilisateur.class, dto.getId());
        if (u == null) {
            throw new IllegalArgumentException("Utilisateur introuvable");
        }
        
        
        Site site = em.find(Site.class, dto.getSiteId());
        if (site == null) {
            throw new IllegalArgumentException("Site introuvable");
        }

        u.setUsername(dto.getUsername());
        u.setNom(dto.getNom());
        u.setPrenom(dto.getPrenom());
        u.setSite(site);
        u.setActive(dto.getActive());
        
        // Profils
        u.getProfiles().clear();
        for (String id : selectedProfileIds) {
            u.getProfiles().add(
                profilService.findById(Long.valueOf(id))
            );
        }

        // 🔑 changement mot de passe (optionnel)
      /*  if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            u.setPassword(BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt()));
        }*/
        
        em.merge(u);
        // 🔍 AUDIT MÉTIER
        auditService.log(
        	"MODIFICATION_UTILISATEUR", securityContext.getUtilisateur(),"Utilisateur modifié : " + dto.getUsername()
        );
        return u;
    }

    @Override
    public void desactiver(Long utilisateurId) {

        Utilisateur u = em.find(Utilisateur.class, utilisateurId);
        if (u != null) {
            u.setActive(false);
        }
        
        // 🔍 AUDIT MÉTIER
        auditService.log(
        	"DESACTIVATION_UTILISATEUR", securityContext.getUtilisateur(),"Utilisateur créé : " + u.getUsername()
        );
    }

    @Override
    public Utilisateur trouverParId(Long id) {
        return em.find(Utilisateur.class, id);
    }

    @Override
    public List<Utilisateur> lister() {

        return em.createQuery(
            "SELECT u FROM Utilisateur u " +
            "ORDER BY u.username",
            Utilisateur.class)
            .getResultList();
    }
    
    @Override
    public void updatePassword(Long userId, String hashedPassword) {
        Utilisateur user = em.find(Utilisateur.class, userId);
        if (user == null) {
            throw new IllegalStateException("Utilisateur introuvable");
        }
        user.setPassword(hashedPassword);
    }
    
    
    @Override
    public String resetPassword(Long userId, Utilisateur admin) {

        Utilisateur user = em.find(Utilisateur.class, userId);

        if (user == null) {
            throw new IllegalStateException("Utilisateur introuvable");
        }

       

        String tempPassword = generateTempPassword();

        user.setPassword(BCrypt.hashpw(tempPassword, BCrypt.gensalt()));
        user.setMustChangePassword(true);

        auditService.log(
            "REINITIALISATION_MOT_DE_PASSE",
            admin,
            "Réinitialisation du mot de passe de " + user.getUsername()
        );

        return tempPassword; // affiché UNE SEULE FOIS
    }

    private String generateTempPassword() {
        return "Tmp@" + System.currentTimeMillis() % 100000;
    }

}
