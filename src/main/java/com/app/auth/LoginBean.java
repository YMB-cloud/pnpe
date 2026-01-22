package com.app.auth;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.app.entity.Site;
import com.app.entity.SiteDTO;
import com.app.entity.Utilisateur;

@Named
@SessionScoped
public class LoginBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String username;
    private String password;
    private Utilisateur connectedUser;

    @Inject
    private AuthenticationService authenticationService;
    
    public String versHome() {
    	return "/secured/dashboard.xhtml?faces-redirect=true";
    }
    
    public String versCompte() {
    	return "/secured/auth/profile.xhtml?faces-redirect=true";
    }
    
    public String login() {

        connectedUser = authenticationService.authenticate(username, password);

        if (connectedUser != null) {
        	FacesContext.getCurrentInstance()
            .getExternalContext()
            .getSessionMap()
            .put("currentUser", connectedUser);
            return "/secured/dashboard.xhtml?faces-redirect=true";
        }
        
       /* if (user.getMustChangePassword()) {
            return "/secured/change-password.xhtml?faces-redirect=true";
        }*/

        UiMessage.swalError("Accès refusé", "Login ou mot de passe incorrect");

        return null;
    }
    
    public String logout() {
    	authenticationService.logout();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();

        // Invalider la session
        externalContext.invalidateSession();
    	return "/index.xhtml?faces-redirect=true";
    }
/*
    public boolean hasRole(String role) {
        return connectedUser.getRoles()
                .stream()
                .anyMatch(r -> r.getCode().equals(role));
    }*/
    
   /* public void creerAdmin() {
    	
    	SiteDTO s = new SiteDTO();
    	s.setCode("AG01");
    	s.setLibelle("Agence centrale");
    	s.setActif(true);
    	Site newSaite = siteService.creer(s);
    	
    	utilisateurService.creerAdmin(newSaite);
    }*/

	public Utilisateur getConnectedUser() {
		return connectedUser;
	}

	public void setConnectedUser(Utilisateur connectedUser) {
		this.connectedUser = connectedUser;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}

