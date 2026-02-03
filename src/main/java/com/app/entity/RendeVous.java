package com.app.entity;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.JoinColumn;

@Entity 
public class RendeVous {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dateHeureRdv;
    private LocalTime heureFinRdv;
    private LocalTime heureDebutRdv;
    private LocalDateTime dateCreation ;
    private String lieu;
    private Integer etatRdv = 0;
    
    
    @OneToMany(
            mappedBy = "rendezvous",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
        )
    private List<RendezVousParticipant> participants = new ArrayList<>();
    
    @ManyToOne
    @JoinColumn(name = "utilisateur_emetteur_id",referencedColumnName = "id")
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
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public Integer getEtatRdv() {
		return etatRdv;
	}
	public void setEtatRdv(Integer etatRdv) {
		this.etatRdv = etatRdv;
	}

	
	

	
	public LocalTime getHeureFinRdv() {
		return heureFinRdv;
	}
	public void setHeureFinRdv(LocalTime heureFinRdv) {
		this.heureFinRdv = heureFinRdv;
	}
	public LocalTime getHeureDebutRdv() {
		return heureDebutRdv;
	}
	public void setHeureDebutRdv(LocalTime heureDebutRdv) {
		this.heureDebutRdv = heureDebutRdv;
	}
	public List<RendezVousParticipant> getParticipants() {
		return participants;
	}
	public void setParticipants(List<RendezVousParticipant> participants) {
		this.participants = participants;
	}
	
	
    
}

