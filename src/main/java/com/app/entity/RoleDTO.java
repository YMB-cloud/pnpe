package com.app.entity;

public class RoleDTO {
	
	private Long id;
    private String code;
    private String libelle;
    private String redirection;
    private Boolean actif = true;
    private Long roleParentId;
    
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
	public Long getRoleParentId() {
		return roleParentId;
	}
	public void setRoleParentId(Long roleParentId) {
		this.roleParentId = roleParentId;
	}

}
