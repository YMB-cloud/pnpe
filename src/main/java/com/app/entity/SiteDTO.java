package com.app.entity;

public class SiteDTO {

    private Long id;
    private String code;
    private String libelle;
    private Boolean actif;
    
    // getters / setters
    
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
	
	public Boolean getActif() {
		return actif;
	}
	public void setActif(Boolean actif) {
		this.actif = actif;
	}
	public String getLibelle() {
		return libelle;
	}
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}

    
}

