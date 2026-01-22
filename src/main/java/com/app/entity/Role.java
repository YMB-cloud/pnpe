package com.app.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String code;

    private String libelle;
    
    private String redirection;
    
    private Boolean actif = true;
    
    /* ===== RÔLE PARENT ===== */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Role parent;

    /* ===== SOUS-RÔLES ===== */
    @OneToMany(mappedBy = "parent", fetch = FetchType.EAGER)
    private Set<Role> children = new HashSet<>();
    
    
    /* ===== MÉTHODES MÉTIER ===== */

    public void addChild(Role child) {
        children.add(child);
        child.setParent(this);
    }

    public void removeChild(Role child) {
        children.remove(child);
        child.setParent(null);
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

	public String getRedirection() {
		return redirection;
	}

	public void setRedirection(String redirection) {
		this.redirection = redirection;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean actif) {
		this.actif = actif;
	}

	public Role getParent() {
		return parent;
	}

	public void setParent(Role parent) {
		this.parent = parent;
	}

	public Set<Role> getChildren() {
		return children;
	}

	public void setChildren(Set<Role> children) {
		this.children = children;
	}
	
	
	public List<Role> getChildrenList() {
	    return new ArrayList<>(children);
	}

	
}

