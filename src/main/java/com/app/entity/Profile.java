package com.app.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.JoinColumn;

@Entity
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String libelle;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "profile_roles",
        joinColumns = @JoinColumn(name = "profile_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles;
    
	private Boolean actif;    
    
    @ManyToMany(mappedBy = "profiles")
    private Set<Utilisateur> utilisateurs = new HashSet<>();
    

    /* ====== MÉTHODES MÉTIER ====== */

    public void addRole(Role role) {
        roles.add(role);
        //role.getProfiles().add(this); // côté inverse
    }

    public void removeRole(Role role) {
        roles.remove(role);
        //role.getProfiles().remove(this);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public Set<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(Set<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}

