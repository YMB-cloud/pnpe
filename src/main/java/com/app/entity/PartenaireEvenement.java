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
import javax.persistence.Transient;
import javax.persistence.JoinColumn;

@Entity 
public class PartenaireEvenement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private int statut;
    private Integer confirmation;
    private Integer confirmationRespecte;
 // Relation 1-à-1 : Un demandeur possède une fiche personne unique
    @ManyToOne
    @JoinColumn(name = "evenement_id", referencedColumnName = "id")
    private Evenement evenement;
    
    @ManyToOne
    @JoinColumn(name = "partenanire_id", referencedColumnName = "id")
    private Partenaire partenaire;
  
    @Transient // JPA ignore ce champ
    public Boolean getConfirmationBoolean() {
        return confirmation != null && confirmation == 1;
    }
    
    @Transient // JPA ignore ce champ
    public Boolean getConfirmationBooleanRespecte() {
        return confirmationRespecte != null && confirmationRespecte == 1;
    }
   
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
	public void setConfirmation(Integer confirmation) {
		this.confirmation = confirmation;
	}
	public Evenement getEvenement() {
		return evenement;
	}
	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}
	public Partenaire getPartenaire() {
		return partenaire;
	}
	public void setPartenaire(Partenaire partenaire) {
		this.partenaire = partenaire;
	}
	public int getConfirmation() {
		return confirmation;
	}
	public void setConfirmation(int confirmation) {
		this.confirmation = confirmation;
	}

	public Integer getConfirmationRespecte() {
		return confirmationRespecte;
	}

	public void setConfirmationRespecte(Integer confirmationRespecte) {
		this.confirmationRespecte = confirmationRespecte;
	}
	
	
	
	
	
    
}

