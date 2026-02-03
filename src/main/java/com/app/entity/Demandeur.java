package com.app.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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
import javax.persistence.ForeignKey;

@Entity  
public class Demandeur {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    
 // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "personne_id", referencedColumnName = "id")
    private Personne personne;
    
    @ManyToOne
    @JoinColumn(name = "utilisateur_id",referencedColumnName = "id")
    private Utilisateur utilisateur;


	// Relation N-à-1 : Plusieurs demandeurs peuvent être rattachés à un même site (agence)
    @ManyToOne
    @JoinColumn(name = "site_id",referencedColumnName = "id")
    private Site site;
    private int statut;
    @Column(nullable = true)
    private Integer  autoemploi;
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Site getSite() {
		return site;
	}
	public void setSite(Site site) {
		this.site = site;
	}
	public int getStatut() {
		return statut;
	}
	public void setStatut(int statut) {
		this.statut = statut;
	}
	public Personne getPersonne() {
		return personne;
	}
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}


    public Utilisateur getUtilisateur() {
		return utilisateur;
	}
	public void setUtilisateur(Utilisateur utilisateur) {
		this.utilisateur = utilisateur;
	}
	public int getAutoemploi() {
		return autoemploi;
	}
	public void setAutoemploi(Integer  autoemploi) {
		this.autoemploi = autoemploi;
	}
	

    
    
}

