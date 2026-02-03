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
public class PersonnelFonctionDemandeurNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int statut;
    private LocalDateTime dateLecture;
 // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "notification_id", referencedColumnName = "id")
    private Notification notification;
    
    @ManyToOne
    @JoinColumn(name = "personnelFonctionDemandeur_id", referencedColumnName = "id")
    private PersonnelFonctionDemandeur personnelFonctionDemandeur;
  
   
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
	public Notification getNotification() {
		return notification;
	}
	public void setNotification(Notification notification) {
		this.notification = notification;
	}
	public LocalDateTime getDateLecture() {
		return dateLecture;
	}
	public void setDateLecture(LocalDateTime dateLecture) {
		this.dateLecture = dateLecture;
	}
	
	
	
	
    
}

