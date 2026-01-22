package com.app.evenement;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.Demandeur;
import com.app.entity.Dispositif;
import com.app.entity.Evenement;
import com.app.entity.Partenaire;
import com.app.entity.PartenaireEvenement;
import com.app.entity.PersonnelFonctionDemandeur;
import com.app.entity.PersonnelFonctionDemandeurEvenement;
import com.app.entity.RendeVous;
import com.app.entity.RendezVousParticipant;
import com.app.entity.TypeEvenement;

@Stateless
public class EvenementService {

    @PersistenceContext
    private EntityManager em;

    public List<Evenement> findAll() {
        return em.createQuery("SELECT r FROM Evenement r", Evenement.class)
                 .getResultList();
    }
    
    public List<Partenaire> findAllPartenaire() {
        return em.createQuery(
                "SELECT r FROM Partenaire r ORDER BY r.personne.nom",
                Partenaire.class
        ).getResultList();
    }
    
    
    public List<Dispositif> listeDispositif() {
        return em.createQuery(
                "SELECT r FROM Dispositif r ORDER BY r.libelle",
                Dispositif.class
        ).getResultList();
    }
    
    public List<PersonnelFonctionDemandeur> findAllDemandeur() {
        return em.createQuery(
                "SELECT r FROM PersonnelFonctionDemandeur r ORDER BY r.demandeur.personne.nom",
                PersonnelFonctionDemandeur.class
        ).getResultList();
    }
    public List<TypeEvenement> findAllTypeEvenement() {
        return em.createQuery(
                "SELECT r FROM TypeEvenement r ",
                TypeEvenement.class
        ).getResultList();
    }
    
    public Long totalEvenementSelonEtat(int etat) {
        return em.createQuery(
                "SELECT COUNT(e) FROM Evenement e WHERE e.etat = :etat",
                Long.class
        )
        .setParameter("etat", etat)
        .getSingleResult();
    }

    
  
    
    public List<PersonnelFonctionDemandeurEvenement> demandeurEvenement(Evenement ev) {
        return em.createQuery(
            "SELECT p FROM PersonnelFonctionDemandeurEvenement p " +
            "WHERE p.evenement.id = :id",
            PersonnelFonctionDemandeurEvenement.class
        )
        .setParameter("id", ev.getId())
        .getResultList();
    }

    public List<PartenaireEvenement> partenairesEvenement(Evenement ev) {
        return em.createQuery(
            "SELECT p FROM PartenaireEvenement p " +
            "WHERE p.evenement.id = :id",
            PartenaireEvenement.class
        )
        .setParameter("id", ev.getId())
        .getResultList();
    }
    
    public Evenement evenementVoulu(Evenement ev) {
        return em.createQuery(
            "SELECT p FROM Evenement p WHERE p.id = :id",
            Evenement.class
        )
        .setParameter("id", ev.getId())
        .getSingleResult();
    }
    
    public Evenement findAvecRelations(Long id) {
        Evenement e = em.createQuery(
            "SELECT e FROM Evenement e WHERE e.id = :id",
            Evenement.class
        ).setParameter("id", id).getSingleResult();

        // ⚡ Forcer le chargement des relations lazy
        e.getPartenaires().size();
        e.getPersonnelsDemandeur().size();
        if (e.getDispositifEvenement() != null) e.getDispositifEvenement().getId(); // force chargement

        return e;
    }



    public boolean changeEtatEvenement(Evenement ev, int etat) {
        int updated = em.createQuery(
                "UPDATE Evenement p SET p.etat = :etat WHERE p.id = :id"
            )
            .setParameter("etat", etat) // ⚡ ajouter le paramètre etat
            .setParameter("id", ev.getId())
            .executeUpdate();

        return updated > 0; // true si au moins 1 ligne a été modifiée
    }

    public boolean updateEvenement(Evenement ev, List<PersonnelFonctionDemandeur> demandeurPersonnels,
    		List<Partenaire> partenaires) {
        try {
             for (PersonnelFonctionDemandeur d : demandeurPersonnels) {
            	 boolean existDeja=false;
	           for(PersonnelFonctionDemandeurEvenement pfde :ev.getPersonnelsDemandeur())
		            	if(pfde.getPersonnelFonctionDemandeur().equals(d)){
		            		 existDeja=true;	 
		            	}
            	 if(!existDeja) {
            		 PersonnelFonctionDemandeurEvenement pr = new PersonnelFonctionDemandeurEvenement();
                     pr.setEvenement(ev);
                     pr.setPersonnelFonctionDemandeur(d);
                     pr.setStatut(1);
                     ev.getPersonnelsDemandeur().add(pr);
            	  }
           	
           
              }
             for(PersonnelFonctionDemandeurEvenement pfde :ev.getPersonnelsDemandeur()) {
            	 boolean existe=false;
            	 for(PersonnelFonctionDemandeur dm :demandeurPersonnels) {
            		 if(pfde.getPersonnelFonctionDemandeur().equals(dm)) {
            			 existe=true;
            		 }
            	 }
            	 if(!existe) {
            		 pfde.setStatut(0);
            	 }
             }
             
            for (Partenaire p : partenaires) {
            	boolean existDejaPartenaire=false;
            	for( PartenaireEvenement pe: ev.getPartenaires()) {
                   if(pe.getPartenaire().equals(p)) {
                	   existDejaPartenaire=true; 
                   }
            	}
            	if(!existDejaPartenaire) {
            		PartenaireEvenement pr = new PartenaireEvenement();
                    pr.setEvenement(ev);
                    pr.setPartenaire(p);
                    pr.setStatut(1);
    				pr.setConfirmation(0);
    				ev.getPartenaires().add(pr);
            	}
            	
				 
            }
            for(PartenaireEvenement pe :ev.getPartenaires()) {
           	 boolean existe=false;
           	 for(Partenaire part :partenaires) {
           		 if(pe.getPartenaire().equals(part)) {
           			 existe=true;
           		 }
           	 }
           	 if(!existe) {
           		 pe.setStatut(0);
           	 }
            }
            
            
            em.merge(ev);   // cascade → persist RendezVousParticipant
            em.flush();       // force l’écriture en base
            
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // ou logger
            return false;
        }
    }

 

 

   
    public boolean creerEvenement(Evenement ev, List<PersonnelFonctionDemandeur> demandeurPersonnels,
    		List<Partenaire> partenaires) {
        try {
            for (PersonnelFonctionDemandeur d : demandeurPersonnels) {
            	PersonnelFonctionDemandeurEvenement pr = new PersonnelFonctionDemandeurEvenement();
                pr.setEvenement(ev);
                pr.setPersonnelFonctionDemandeur(d);
                pr.setStatut(1);
               
				
				 ev.getPersonnelsDemandeur().add(pr);
				 
            }
            for (Partenaire p : partenaires) {
            	PartenaireEvenement pr = new PartenaireEvenement();
                pr.setEvenement(ev);
                pr.setPartenaire(p);
                pr.setStatut(1);
				pr.setConfirmation(0);
				ev.getPartenaires().add(pr);
				 
            }

            em.persist(ev);   // cascade → persist RendezVousParticipant
            em.flush();       // force l’écriture en base
            
            return true;
        } catch (Exception e) {
            e.printStackTrace(); // ou logger
            return false;
        }
    }
    

    public void delete(Evenement ev) {
        em.remove(em.merge(ev));
    }
}
