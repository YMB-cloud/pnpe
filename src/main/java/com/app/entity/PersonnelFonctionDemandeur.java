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
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity
public class PersonnelFonctionDemandeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer confirmation;
    
    private int statut;
 // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "demandeur_id", referencedColumnName = "id")
    private Demandeur demandeur;
    
 // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "personnel_fonction_id", referencedColumnName = "id")
    private PersonnelFonction personnelFonction;
 
    // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "utilisateur_update_id", referencedColumnName = "id")
    private Utilisateur utilisateur ;
 
	
	
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
	public Demandeur getDemandeur() {
		return demandeur;
	}
	public void setDemandeur(Demandeur demandeur) {
		this.demandeur = demandeur;
	}
	public PersonnelFonction getPersonnelFonction() {
		return personnelFonction;
	}
	public void setPersonnelFonction(PersonnelFonction personnelFonction) {
		this.personnelFonction = personnelFonction;
	}
	
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public void setConfirmation(Integer confirmation) {
		this.confirmation = confirmation;
	}
	public int getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(int confirmation) {
		this.confirmation = confirmation;
	}
    
    
}

