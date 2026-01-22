package com.app.auth;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import com.app.entity.Profile;
import com.app.entity.Role;
import com.app.entity.Site;
import com.app.entity.Utilisateur;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Named
@SessionScoped
public class SecurityContextImpl implements SecurityContext, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Utilisateur utilisateur;
    private Site site;
    private Set<Role> roles = new HashSet<>();

    @Override
    public void init(Utilisateur utilisateur) {

        this.utilisateur = utilisateur;
        this.site = utilisateur.getSite();

        roles.clear();

        for (Profile profil : utilisateur.getProfiles()) {
        		for(Role ir: profil.getRoles()) {
        			roles.add(ir);
        			if(ir.getParent()!=null) {
        				roles.add(ir.getParent());
        			}
        		}
        }
    }

    @Override
    public boolean hasRole(String codeRole) {
        return roles.stream()
                .anyMatch(r -> r.getCode().equalsIgnoreCase(codeRole));
    }

    @Override
    public boolean isAuthenticated() {
        return utilisateur != null;
    }

    @Override
    public Utilisateur getUtilisateur() {
        return utilisateur;
    }

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public Set<Profile> getProfils() {
        if (utilisateur == null || utilisateur.getProfiles() == null) {
            return new HashSet<>();
        }
        return utilisateur.getProfiles();
    }


    @Override
    public Set<Role> getRoles() {
        return roles;
    }

    @Override
    public void clear() {
        utilisateur = null;
        site = null;
        roles.clear();
    }
    
    
    @Override
    public Utilisateur getCurrentUser() {
        FacesContext ctx = FacesContext.getCurrentInstance();
        if (ctx == null) return null;

        return (Utilisateur)
            ctx.getExternalContext()
               .getSessionMap()
               .get("currentUser");
    }
}
