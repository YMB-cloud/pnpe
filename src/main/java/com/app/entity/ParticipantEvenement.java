package com.app.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.JoinColumn;

@Entity
public class ParticipantEvenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int statut;
 // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "personnelFonctionDemandeur_id", referencedColumnName = "id")
    private PersonnelFonctionDemandeur personnelFonctionDemandeur;
    
 // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "evenement_id", referencedColumnName = "id")
    private Evenement evenement;
 
   
 
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public PersonnelFonctionDemandeur getPersonnelFonctionDemandeur() {
		return personnelFonctionDemandeur;
	}
	public void setPersonnelFonctionDemandeur(PersonnelFonctionDemandeur personnelFonctionDemandeur) {
		this.personnelFonctionDemandeur = personnelFonctionDemandeur;
	}
	public Evenement getEvenement() {
		return evenement;
	}
	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}
	
	
	
    
}

