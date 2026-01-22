package com.app.auth;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.SessionContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.app.entity.Profile;
import com.app.entity.RoleDTO;
import com.app.entity.Site;
import com.app.entity.Utilisateur;
import com.app.entity.UtilisateurDTO;

@Named
@ViewScoped
public class UtilisateurBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    private UtilisateurService utilisateurService;
	
	@Inject
	private SiteService siteService;
	
	@Inject
	private ProfilService profilService;
	
    @Inject
    private SecurityContext securityContext;

   /* @Inject
    private SessionContext session;*/ // contient site courant

    private UtilisateurDTO utilisateur = new UtilisateurDTO();
    private List<Utilisateur> utilisateurs;
    private List<Site> sites;
    private List<Profile> profiles;
    
    private List<String>  selectedProfileIds;

    @PostConstruct
    public void init() {
        utilisateurs = utilisateurService.lister();
        sites = siteService.listerTous();
        profiles = profilService.lister();
    }
    
    public void initUser() {
    	utilisateur = new UtilisateurDTO();
    	selectedProfileIds = new ArrayList<String>();
    }

    public void creer() {
        utilisateurService.creer(utilisateur,selectedProfileIds);
        utilisateurs = utilisateurService.lister();
        UiMessage.swalSuccess("Enregistrement réussi", "Utilisateur créé avec succès");
    }
    
    public void modifier() {
        utilisateurService.modifier(utilisateur,selectedProfileIds);
        utilisateurs = utilisateurService.lister();
        UiMessage.swalSuccess("Modification réussie", "Utilisateur modifié avec succès");
    }
    
    public void chargerUserToUpdate(Utilisateur u) {
    	selectedProfileIds = new ArrayList<String>();
    	utilisateur.setId(u.getId());
    	utilisateur.setUsername(u.getUsername());
    	utilisateur.setNom(u.getNom());
    	utilisateur.setPrenom(u.getPrenom());
    	utilisateur.setSiteId(u.getSite().getId());
    	utilisateur.setActive(u.getActive());
    	
        // Pré-sélection profils
        selectedProfileIds = u.getProfiles().stream()
                .map(p -> p.getId().toString())
                .collect(Collectors.toList());
    }
    
    public void resetPassword(Utilisateur u) {

        String tempPassword = utilisateurService.resetPassword(
            u.getId(),
            securityContext.getUtilisateur()
        );

        UiMessage.swalSuccess(
            "Mot de passe réinitialisé",
            "Mot de passe temporaire : " + tempPassword
        );
    }
    
    public String versUsers() {
    	
    	return "/secured/auth/users.xhtml?faces-redirect=true";
    }

    public void desactiver(Long id) {
        utilisateurService.desactiver(id);
        //utilisateurs = utilisateurService.listerParSite(session.getSiteId());
    }

	public List<Utilisateur> getUtilisateurs() {
		return utilisateurs;
	}

	public void setUtilisateurs(List<Utilisateur> utilisateurs) {
		this.utilisateurs = utilisateurs;
	}

	public UtilisateurDTO getUtilisateur() {
		return utilisateur;
	}

	public void setUtilisateur(UtilisateurDTO utilisateur) {
		this.utilisateur = utilisateur;
	}

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}



	public List<String>  getSelectedProfileIds() {
		return selectedProfileIds;
	}

	public void setSelectedProfileIds(List<String>  selectedProfileIds) {
		this.selectedProfileIds = selectedProfileIds;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}
}
