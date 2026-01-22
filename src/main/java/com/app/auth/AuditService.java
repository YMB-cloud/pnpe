package com.app.auth;

import java.time.LocalDateTime;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.AuditLog;
import com.app.entity.Utilisateur;

@Stateless
public class AuditService {

    @PersistenceContext
    private EntityManager em;

    public void log(String action, Utilisateur u,String commentaire) {
        AuditLog log = new AuditLog();
        log.setAction(action);
        log.setUsername(u.getUsername());
        log.setCommentaire(commentaire);
        log.setActionDate(LocalDateTime.now());
        log.setUser(u);
        em.persist(log);
    }
}
