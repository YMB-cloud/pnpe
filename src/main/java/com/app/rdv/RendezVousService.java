package com.app.rdv;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.Demandeur;
import com.app.entity.RendeVous;
import com.app.entity.RendezVousParticipant;

@Stateless
public class RendezVousService {

    @PersistenceContext
    private EntityManager em;

    public List<RendeVous> findAll() {
        return em.createQuery("SELECT r FROM RendeVous r", RendeVous.class)
                 .getResultList();
    }
    
    
    
    public List<Demandeur> findAllDemandeurs() {
        return em.createQuery("SELECT r FROM Demandeur r", Demandeur.class)
                 .getResultList();
    }


    public void save(RendeVous rdv) {
        if (rdv.getId() == null) {
        	
            em.persist(rdv);
        } else {
            em.merge(rdv);
            
        }
    }
    public boolean creerRendezVous(RendeVous rv, List<Demandeur> demandeurs) {
        try {
            for (Demandeur d : demandeurs) {
                RendezVousParticipant pr = new RendezVousParticipant();
                pr.setRendezvous(rv);
                pr.setDemandeur(d);

                rv.getParticipants().add(pr);
            }

            em.persist(rv);   // cascade → persist RendezVousParticipant
            em.flush();       // force l’écriture en base

            return true;
        } catch (Exception e) {
            e.printStackTrace(); // ou logger
            return false;
        }
    }

    public void delete(RendeVous rdv) {
        em.remove(em.merge(rdv));
    }
}
