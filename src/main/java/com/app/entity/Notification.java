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
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private LocalDateTime dateNotif ;
    private String message;
    
    @ManyToOne
    @JoinColumn(name = "personnel_fonction_id",referencedColumnName = "id")
    private PersonnelFonction personnelFonction;
    
    @ManyToOne
    @JoinColumn(name = "evenement_id",referencedColumnName = "id")
    private Evenement evenement;
    
    
    @ManyToOne
    @JoinColumn(name = "type_notification_id",referencedColumnName = "id")
    private TypeNotification typeNotification;
    
	
	public Long getId() {
		return id;
	}
	public TypeNotification getTypeNotification() {
		return typeNotification;
	}
	public void setTypeNotification(TypeNotification typeNotification) {
		this.typeNotification = typeNotification;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	
	public PersonnelFonction getPersonnelFonction() {
		return personnelFonction;
	}
	public void setPersonnelFonction(PersonnelFonction personnelFonction) {
		this.personnelFonction = personnelFonction;
	}
	public LocalDateTime getDateNotif() {
		return dateNotif;
	}
	public void setDateNotif(LocalDateTime dateNotif) {
		this.dateNotif = dateNotif;
	}
	public Evenement getEvenement() {
		return evenement;
	}
	public void setEvenement(Evenement evenement) {
		this.evenement = evenement;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
    
}

