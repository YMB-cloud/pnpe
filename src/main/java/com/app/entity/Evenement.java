package com.app.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
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
public class Evenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dateEvenement;
    private LocalDateTime dateDebut ;
    private LocalDate datePrevisionnelleFin ;
    private LocalDateTime dateFin ;
    private LocalDateTime dateCreation;
    private String titre;
    private String lienVisio;
    private String lienVision ;
    
    private String description;
    private Integer etat;
    private String lieu;
    private String objectif;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispositif_id") 
    private Dispositif dispositifEvenement;
    @OneToMany(
            mappedBy = "evenement",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
        )
    private List<PersonnelFonctionDemandeurEvenement> personnelsDemandeur = new ArrayList<>();
    
    
    @OneToMany(
            mappedBy = "evenement",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
        )
    private List<Notification> listeNotif = new ArrayList<>();
    
    
    
    @OneToMany(
            mappedBy = "evenement",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
        )
    private List<PartenaireEvenement> partenaires = new ArrayList<>();
    
	@ManyToOne
    @JoinColumn(name = "utilisateur_id",referencedColumnName = "id")
    private Utilisateur utilisateur; 
	
    @ManyToOne
    @JoinColumn(name = "type_evenement_id",referencedColumnName = "id")
    private TypeEvenement typeEvenement; 
    
	public TypeEvenement getTypeEvenement() {
		return typeEvenement;
	}
	public void setTypeEvenement(TypeEvenement typeEvenement) {
		this.typeEvenement = typeEvenement;
	}
	public LocalDateTime getDateEvenement() {
		return dateEvenement;
	}
	public void setDateEvenement(LocalDateTime dateEvenement) {
		this.dateEvenement = dateEvenement;
	}
	public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public LocalDateTime getDateDebut() {
		return dateDebut;
	}
	public void setDateDebut(LocalDateTime dateDebut) {
		this.dateDebut = dateDebut;
	}
	public LocalDate getDatePrevisionnelleFin() {
		return datePrevisionnelleFin;
	}
	public void setDatePrevisionnelleFin(LocalDate datePrevisionnelleFin) {
		this.datePrevisionnelleFin = datePrevisionnelleFin;
	}
	public LocalDateTime getDateFin() {
		return dateFin;
	}
	public void setDateFin(LocalDateTime dateFin) {
		this.dateFin = dateFin;
	}
	
	public List<PersonnelFonctionDemandeurEvenement> getPersonnelsDemandeur() {
		return personnelsDemandeur;
	}
	public void setPersonnelsDemandeur(List<PersonnelFonctionDemandeurEvenement> personnelsDemandeur) {
		this.personnelsDemandeur = personnelsDemandeur;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<PartenaireEvenement> getPartenaires() {
		return partenaires;
	}
	public void setPartenaires(List<PartenaireEvenement> partenaires) {
		this.partenaires = partenaires;
	}
	public LocalDateTime getDateCreation() {
		return dateCreation;
	}
	public void setDateCreation(LocalDateTime dateCreation) {
		this.dateCreation = dateCreation;
	}
	public Integer getEtat() {
		return etat;
	}
	public void setEtat(Integer etat) {
		this.etat = etat;
	}
	public String getLieu() {
		return lieu;
	}
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}
	public String getObjectif() {
		return objectif;
	}
	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}
	public String getLienVision() {
		return lienVision;
	}
	public void setLienVision(String lienVision) {
		this.lienVision = lienVision;
	}
	public Dispositif getDispositifEvenement() {
		return dispositifEvenement;
	}
	public void setDispositifEvenement(Dispositif dispositifEvenement) {
		this.dispositifEvenement = dispositifEvenement;
	}
	public String getLienVisio() {
		return lienVisio;
	}
	public void setLienVisio(String lienVisio) {
		this.lienVisio = lienVisio;
	}
	public List<Notification> getListeNotif() {
		return listeNotif;
	}
	public void setListeNotif(List<Notification> listeNotif) {
		this.listeNotif = listeNotif;
	}
	
    
}

