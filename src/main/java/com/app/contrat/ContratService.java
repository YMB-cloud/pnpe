package com.app.contrat;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.Contrat;
import com.app.entity.Demandeur;
import com.app.entity.Dispositif;
import com.app.entity.Evenement;
import com.app.entity.Morale;
import com.app.entity.NoteContrat;
import com.app.entity.Notification;
import com.app.entity.Partenaire;
import com.app.entity.PartenaireEvenement;
import com.app.entity.PersonnelFonctionDemandeur;
import com.app.entity.PersonnelFonctionDemandeurEvenement;
import com.app.entity.PersonnelFonctionDemandeurNotification;
import com.app.entity.RendeVous;
import com.app.entity.RendezVousParticipant;
import com.app.entity.TypeContrat;
import com.app.entity.TypeEvenement;

@Stateless
public class ContratService {

    @PersistenceContext
    private EntityManager em;
    
    public List<Demandeur> listeDemandeur() {

        List<Demandeur> result = em.createQuery(
            "SELECT DISTINCT d FROM Demandeur d LEFT JOIN FETCH d.personne" ,
            Demandeur.class
        ).getResultList();

        return result;
    }
    
    public List<Morale> listeMorale() {

        List<Morale> result = em.createQuery(
            "SELECT m FROM Morale m ",
            Morale.class
        ).getResultList();

        return result;
    }
    
    
    
    
    public List<TypeContrat> listeTypeContrat() {

        List<TypeContrat> result = em.createQuery(
            "SELECT  tc FROM TypeContrat tc",
            TypeContrat.class
        ).getResultList();

        return result;
    }
    
    
    public List<Contrat> listeContrat() {

        List<Contrat> result = em.createQuery(
            "SELECT DISTINCT c FROM Contrat c " +
            "LEFT JOIN FETCH c.demandeur " +
            "LEFT JOIN FETCH c.morale",
            Contrat.class
        ).getResultList();

        return result;
    }
    
    public Contrat contratCapte(Contrat ctr) {

        Contrat c= em.createQuery(
            "SELECT DISTINCT c FROM Contrat c " +
            "LEFT JOIN FETCH c.demandeur " +
            "LEFT JOIN FETCH c.morale " +
            
            "WHERE c.id = :id",
            Contrat.class
        )
        .setParameter("id", ctr.getId())
        .getSingleResult();
        c.getListeNoteContrat().size();
        if (c.getDemandeur() != null) c.getDemandeur().getId(); // force chargement
        if (c.getMorale() != null) c.getMorale().getId(); // force chargement
        return c;
    }

    
   


    
    
    
    public boolean creerContrat(Contrat contrat) {
        try {
            
            em.persist(contrat);   // cascade → persist RendezVousParticipant
            em.flush();       // force l’écriture en base
            
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // ou logger
            return false;
        }
    }
    public boolean creerNoteContrat(NoteContrat noteContrat) {
        try {
            
            em.persist(noteContrat);   // cascade → persist RendezVousParticipant
            em.flush();       // force l’écriture en base
            
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // ou logger
            return false;
        }
    }


}
