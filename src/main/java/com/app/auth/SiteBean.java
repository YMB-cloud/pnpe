package com.app.auth;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import com.app.entity.Site;
import com.app.entity.SiteDTO;

@Named
@ViewScoped
public class SiteBean implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
    private SiteService siteService;

    private SiteDTO site = new SiteDTO();
    private List<Site> sites;

    @PostConstruct
    public void init() {
        setSites(siteService.listerTous());
    }
    
    public void chargerSiteToUpdate(Site s) {
    	site.setId(s.getId());
    	site.setCode(s.getCode());
    	site.setLibelle(s.getLibelle());
    	site.setActif(s.getActif());
    }
    
    public String versSites() {
    	
    	return "/secured/auth/sites.xhtml?faces-redirect=true";
    }
    
    public void initSite() {
    	site = new SiteDTO();
    }

    public void creer() {
    	try {
            siteService.creer(site);
            setSites(siteService.listerTous());
            site = new SiteDTO();
            UiMessage.swalSuccess("Création de site", "Site enregistré");
    	}catch(IllegalStateException e) {
            FacesContext.getCurrentInstance().addMessage(
                    null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Erreur", e.getMessage()    )// 🔥 "Code site déjà utilisé"
            );
    	}

    }

    public void modifier() {
        siteService.modifier(site);
        setSites(siteService.listerTous());
        UiMessage.swalSuccess("Modification de site", "Site modifié");
    }

    public void desactiver(Long id) {
        siteService.desactiver(id);
        setSites(siteService.listerTous());
    }

	public List<Site> getSites() {
		return sites;
	}

	public void setSites(List<Site> sites) {
		this.sites = sites;
	}

	public SiteDTO getSite() {
		return site;
	}

	public void setSite(SiteDTO site) {
		this.site = site;
	}
}

