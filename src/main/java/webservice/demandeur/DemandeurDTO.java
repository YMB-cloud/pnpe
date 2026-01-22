package webservice.demandeur;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.app.entity.Demandeur;
import com.app.entity.Personne;
import com.app.entity.Site;
import com.app.entity.Utilisateur;

public class DemandeurDTO {

	      private Long id;
	 
	  
	      private Personne personne;
	
	      private Utilisateur utilisateur;


	      private Site site;
	      private int statut;
	      private boolean autoemploi;

	      
	      
	  	public Long getId() {
	  		return id;
	  	}
	  	public void setId(Long id) {
	  		this.id = id;
	  	}
	  	public Site getSite() {
	  		return site;
	  	}
	  	public void setSite(Site site) {
	  		this.site = site;
	  	}
	  	public boolean isAutoemploi() {
			return autoemploi;
		}
		public void setAutoemploi(boolean autoemploi) {
			this.autoemploi = autoemploi;
		}
		public int getStatut() {
	  		return statut;
	  	}
	  	public void setStatut(int statut) {
	  		this.statut = statut;
	  	}
	  	public Personne getPersonne() {
	  		return personne;
	  	}
	  	public void setPersonne(Personne personne) {
	  		this.personne = personne;
	  	}


	      public Utilisateur getUtilisateur() {
	  		return utilisateur;
	  	}
	  	public void setUtilisateur(Utilisateur utilisateur) {
	  		this.utilisateur = utilisateur;
	  	}


	public DemandeurDTO(Demandeur d) {
        this.id = d.getId();
        this.personne = d.getPersonne();
        this.utilisateur = d.getUtilisateur();
        this.site = d.getSite();
        this.statut = d.getStatut();
        
    }

    // getters & setters
}
