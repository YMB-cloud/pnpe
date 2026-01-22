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
import javax.persistence.JoinColumn;

@Entity
public class Partenaire {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "personne_id",referencedColumnName = "id")
    private Personne personne;
    @ManyToOne
    @JoinColumn(name = "morale_id",referencedColumnName = "id")
    private Morale morale;
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Personne getPersonne() {
		return personne;
	}
	public void setPersonne(Personne personne) {
		this.personne = personne;
	}
	public Morale getMorale() {
		return morale;
	}
	public void setMorale(Morale morale) {
		this.morale = morale;
	}
	
	
    
}

