package com.app.auth;

import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.Site;
import com.app.entity.SiteDTO;

@Stateless
public class SiteServiceImpl implements SiteService {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private AuditService auditService;
    
    @Inject
    private SecurityContext securityContext;

    @Override
    public Site creer(SiteDTO dto) {

        Long count = em.createQuery(
            "SELECT COUNT(s) FROM Site s WHERE s.code = :code",
            Long.class)
            .setParameter("code", dto.getCode())
            .getSingleResult();

        if (count > 0) {
            throw new IllegalStateException("Code site déjà utilisé");
        }

        Site site = new Site();
        site.setCode(dto.getCode());
        site.setLibelle(dto.getLibelle());
        site.setActif(true);

        em.persist(site);
        
        // 🔍 AUDIT MÉTIER
        auditService.log(
        	"CREATION_SITE", securityContext.getUtilisateur(),"Site créé : " + site.getLibelle()
        );
        
        
        return site;
    }

    @Override
    public Site modifier(SiteDTO dto) {

        Site site = em.find(Site.class, dto.getId());
        if (site == null) {
            throw new IllegalArgumentException("Site introuvable");
        }

        site.setCode(dto.getCode());
        site.setLibelle(dto.getLibelle());
        site.setActif(dto.getActif());

        
        // 🔍 AUDIT MÉTIER
        auditService.log(
        	"MODIFICATION_SITE", securityContext.getUtilisateur(),"Site modifié : " + site.getLibelle()
        );
        return site;
    }

    @Override
    public void desactiver(Long siteId) {

        Site site = em.find(Site.class, siteId);
        if (site == null) return;

        Long nbUtilisateurs = em.createQuery(
            "SELECT COUNT(u) FROM Utilisateur u WHERE u.site.id = :siteId",
            Long.class)
            .setParameter("siteId", siteId)
            .getSingleResult();

        if (nbUtilisateurs > 0) {
            // 🔍 AUDIT MÉTIER
            auditService.log(
            	"DESACTIVATION_SITE", securityContext.getUtilisateur(),"Site désactivé : " + site.getLibelle()
            );
            site.setActif(false);
            em.merge(site);
        } else {

            // 🔍 AUDIT MÉTIER
            auditService.log(
            	"SUPPRESSION_SITE", securityContext.getUtilisateur(),"Site effacé : " + site.getLibelle()
            );
            em.remove(site);
        }
    }

    @Override
    public Site trouverParId(Long id) {
        return em.find(Site.class, id);
    }

    @Override
    public List<Site> listerTous() {

        return em.createQuery(
            "SELECT s FROM Site s ORDER BY s.libelle",
            Site.class)
            .getResultList();
    }
}

