package com.app.auth;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.ProfilDTO;
import com.app.entity.Profile;
import com.app.entity.Role;
import com.app.entity.RoleDTO;
import com.app.entity.Site;

@Stateless
public class ProfilServiceImpl implements ProfilService {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private SecurityContext securityContext;
    
    @Inject
    private AuditService auditService;

    @Override
    public Profile creer(ProfilDTO dto) {

        Long count = em.createQuery(
            "SELECT COUNT(p) FROM Profile p " +
            "WHERE p.libelle = :nom ",
            Long.class)
            .setParameter("nom", dto.getLibelle())
            .getSingleResult();

        if (count > 0) {
            throw new IllegalStateException("Profil déjà existant");
        }



        Profile p = new Profile();
        p.setLibelle(dto.getLibelle());
        p.setCode(dto.getCode());
       // p.setRoles(dto.get);
        p.setActif(true);

        em.persist(p);
        
        // 🔍 AUDIT MÉTIER
        auditService.log(
            "CREATION_PROFIL", securityContext.getUtilisateur(),"Profile créé : " + p.getLibelle()
        );
        
        
        return p;
    }
    
    @Override
    public Profile findById(Long id) {
        return em.find(Profile.class, id);
    }
    
    @Override
    public Role creerRole(RoleDTO dto) {

        Long count = em.createQuery(
            "SELECT COUNT(p) FROM Role p " +
            "WHERE p.libelle = :nom ",
            Long.class)
            .setParameter("nom", dto.getLibelle())
            .getSingleResult();

        if (count > 0) {
            throw new IllegalStateException("Role déjà existant");
        }



        Role p = new Role();
        p.setLibelle(dto.getLibelle());
        p.setCode(dto.getCode());
        p.setRedirection(dto.getRedirection());
        p.setActif(true);

        em.persist(p);
        
        // 🔍 AUDIT MÉTIER
        auditService.log(
            "CREATION_ROLE", securityContext.getUtilisateur(),"Role créé : " + p.getLibelle()
        );
        
        
        return p;
    }

    @Override
    public Profile modifier(ProfilDTO dto) {

        Profile p = em.find(Profile.class, dto.getId());
        if (p == null) {
            throw new IllegalArgumentException("Profile introuvable");
        }

        p.setCode(dto.getCode());
        p.setLibelle(dto.getLibelle());
        p.setActif(dto.getActif());
        
        // 🔍 AUDIT MÉTIER
        auditService.log(
            "MODIFICATION_PROFILE", securityContext.getUtilisateur(),"Profile modifié : " + p.getLibelle()
        );

        return p;
    }
    
    @Override
    public void ajouterChildRole(RoleDTO parent,RoleDTO child) {

        Role parentRole = em.find(Role.class, parent.getId());
        if (parentRole == null) {
            throw new IllegalArgumentException("Role introuvable");
        }

        Role childRole = new Role();
        childRole.setCode(child.getCode());
        childRole.setLibelle(child.getLibelle());
        childRole.setActif(child.getActif());
       
        parentRole.addChild(childRole);
        em.persist(childRole);
        
        // 🔍 AUDIT MÉTIER
        auditService.log(
            "AJOUR_SOUS_ROLE", securityContext.getUtilisateur(),"Sous role ajouté : " + childRole.getLibelle()
        );

    }
    
    @Override
    public void ajouterRoles(Long profileId,  Set<Role> roleList) {

        Profile profile = em.find(Profile.class, profileId);
        
        profile.getRoles().clear();
        profile.setRoles(roleList);

      

        em.merge(profile);
    }
    
    @Override
    public Role modifierRole(RoleDTO dto) {

    	Role p = em.find(Role.class, dto.getId());
        if (p == null) {
            throw new IllegalArgumentException("Role introuvable");
        }

        p.setCode(dto.getCode());
        p.setLibelle(dto.getLibelle());
        p.setActif(dto.getActif());
        p.setRedirection(dto.getRedirection());
        
        // 🔍 AUDIT MÉTIER
        auditService.log(
            "MODIFICATION_ROLE", securityContext.getUtilisateur(),"Role modifié : " + p.getLibelle()
        );

        return p;
    }

    @Override
    public void desactiver(Long profilId) {

        Profile p = em.find(Profile.class, profilId);
        if (p == null) return;

        Long nbUsers = em.createQuery(
            "SELECT COUNT(u) FROM Utilisateur u JOIN u.profils pr " +
            "WHERE pr.id = :profilId",
            Long.class)
            .setParameter("profilId", profilId)
            .getSingleResult();

        if (nbUsers > 0) {
            // 🔍 AUDIT MÉTIER
            auditService.log(
                "SUPRESSION_PROFIL", securityContext.getUtilisateur(),"Profile désactivé : " + p.getLibelle()
            );
            p.setActif(false);
            em.merge(p);
        } else {
            // 🔍 AUDIT MÉTIER
            auditService.log(
            	"SUPRESSION_PROFIL", securityContext.getUtilisateur(),"Profile effacé : " + p.getLibelle()
            );
            em.remove(p);
        }
    }
    
    @Override
    public void desactiverRole(Long roleId) {
    	
    	Role p = em.find(Role.class, roleId);
        if (p == null) return;

        Long nbProfiles = em.createQuery(
            "SELECT COUNT(u) FROM Profile u JOIN u.roles pr " +
            "WHERE pr.id = :roleId",
            Long.class)
            .setParameter("roleId", roleId)
            .getSingleResult();

        if (nbProfiles > 0) {
            // 🔍 AUDIT MÉTIER
            auditService.log(
                "DESACTIVATION_ROLE", securityContext.getUtilisateur(),"Role désactivé : " + p.getLibelle()
            );
            p.setActif(false);
            em.merge(p);
        } else {
        	
        	if(p.getParent()!= null) {
                // 🔍 AUDIT MÉTIER
                auditService.log(
                	"SUPRESSION_ROLE", securityContext.getUtilisateur(),"Role effacé : " + p.getLibelle()
                );
                em.remove(p);
        	}else {
        		if(p.getChildren().size() > 0) {
        			 throw new IllegalStateException("Ce role contient des sous-roles! Impossible de le supprimer.");
        		}else {
                    // 🔍 AUDIT MÉTIER
                    auditService.log(
                    	"SUPRESSION_ROLE", securityContext.getUtilisateur(),"Role effacé : " + p.getLibelle()
                    );
                    em.remove(p);
        		}
        	}
        	

        }
    }

    @Override
    public Profile trouverParId(Long id) {
        return em.find(Profile.class, id);
    }

    @Override
    public List<Profile> lister() {
        return em.createQuery("SELECT p FROM Profile p ORDER BY p.libelle", Profile.class).getResultList();
    }
    
    @Override
    public List<Role> listerRole() {
       // return em.createQuery("SELECT distinct p FROM Role p LEFT JOIN FETCH p.children where p.parent is null ORDER BY p.libelle", Role.class).getResultList();
        return em.createQuery("SELECT  p FROM Role p where p.parent is null ORDER BY p.libelle", Role.class).getResultList();
    }
}

