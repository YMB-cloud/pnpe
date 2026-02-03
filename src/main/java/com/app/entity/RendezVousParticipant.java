package com.app.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity 
public class RendezVousParticipant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateHeureRdv;
    private LocalDateTime dateCreation ;
    @ManyToOne
    @JoinColumn(name = "rendezvous_id",referencedColumnName = "id")
    private RendeVous rendezvous;
    @ManyToOne
    @JoinColumn(name = "demandeur_id",referencedColumnName = "id")
    private Demandeur demandeur;
    @ManyToOne
    @JoinColumn(name = "utilisateur_id",referencedColumnName = "id")
    private Utilisateur utilisateurEmetteur;
    
    private String observation;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDateHeureRdv() {
		return dateHeureRdv;
	}
	public void setDateHeureRdv(LocalDateTime dateHeureRdv) {
		this.dateHeureRdv = dateHeureRdv;
	}
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Utilisateur getUtilisateurEmetteur() {
		return utilisateurEmetteur;
	}
	public void setUtilisateurEmetteur(Utilisateur utilisateurEmetteur) {
		this.utilisateurEmetteur = utilisateurEmetteur;
	}
	public String getObservation() {
		return observation;
	}
	public void setObservation(String observation) {
		this.observation = observation;
	}
	public RendeVous getRendezvous() {
		return rendezvous;
	}
	public void setRendezvous(RendeVous rendezvous) {
		this.rendezvous = rendezvous;
	}
	public Demandeur getDemandeur() {
		return demandeur;
	}
	public void setDemandeur(Demandeur demandeur) {
		this.demandeur = demandeur;
	}
	
	
    
}

