package com.app.rdv;

import java.io.Serializable;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;

import com.app.auth.LoginBean;
import com.app.auth.UiMessage;
import com.app.entity.Demandeur;
import com.app.entity.RendeVous;
import com.app.entity.RendezVousParticipant;
import com.app.entity.RoleDTO;

@Named
@ViewScoped
public class RendezVousBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private RendezVousService rendezVousService; // service pur

    @Inject
    private LoginBean loginBean; // service pur
    
    private ScheduleModel eventModel;
    private List<RendeVous> listeRendeVous;
    private ScheduleEvent<?> selectedEvent;
    private RendeVous selectedRdv;
    private Date heureDebutRdv;
    private Date heureFinRdv;

    private LocalDateTime dateRdv;
    private String lieu;
    private String objectif;
    private List<Demandeur> listeParticipReelRdv;
    private List<Demandeur> listeParticipRdv;

  

	@PostConstruct
    public void init() {
        listeRendeVous = rendezVousService.findAll();
        listeParticipRdv = rendezVousService.findAllDemandeurs();
        listeParticipReelRdv=new ArrayList<>();

        eventModel = new DefaultScheduleModel();
        for (RendeVous rv : listeRendeVous) {
            DefaultScheduleEvent<?> event =
                    DefaultScheduleEvent.builder()
                        .id(rv.getId().toString())
                        .title(rv.getLieu())
                        .description(rv.getObservation())
                        .startDate(rv.getDateHeureRdv())
                        .endDate(rv.getDateHeureRdv())
                        .build();
            eventModel.addEvent(event);
        }
    }
    
    
 public void prepareEdit(RendeVous rdv){
	   this.selectedRdv = rdv;
	  
	   System.out.println("mon RDV :" +this.selectedRdv );
	   
   }
 public void ajouterListeParticipantRdv(Demandeur dem){
	 System.out.println("debut:"+dem.getPersonne().getNom());
         if (!listeParticipReelRdv.contains(dem)) {
        	 listeParticipReelRdv.add(dem);
        	 listeParticipReelRdv.sort(
     	            Comparator.comparing(
     	                p -> p.getPersonne().getNom(),
     	                String.CASE_INSENSITIVE_ORDER
     	            )
     	        );
        	
         }  
	   
 }
 public void confirmCreeRdv() {
	    // Rien ici, la confirmation est gérée côté JS/Swal
	}
 public void reset(){
	 
	   
 }
 
 
 public void creeRdv() {

	   try {
		   
		   
		   
		   
	        RendeVous rendezVous = new RendeVous();
	        LocalTime heureFin = heureFinRdv.toInstant()
	                .atZone(ZoneId.systemDefault())
	                .toLocalTime();
	        LocalTime heureDebut = heureDebutRdv.toInstant()
	                .atZone(ZoneId.systemDefault())
	                .toLocalTime();
	        
	        rendezVous.setDateCreation(LocalDateTime.now());
	        rendezVous.setDateHeureRdv(dateRdv);
	        rendezVous.setEtatRdv(0);
	        rendezVous.setHeureDebutRdv(heureDebut);
	        rendezVous.setHeureFinRdv(heureFin);
	        rendezVous.setLieu(lieu);
	        rendezVous.setObservation(objectif);
	        rendezVous.setUtilisateurEmetteur(loginBean.getConnectedUser());

	        boolean success = rendezVousService.creerRendezVous(
	                rendezVous,
	                listeParticipReelRdv
	        );

	        if (success) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(
	                    FacesMessage.SEVERITY_INFO,
	                    "Succès",
	                    "Le rendez-vous a été créé avec succès"
	                )
	                
	            );
	            UiMessage.swalSuccessAndReload(
	                    "Gestion des rendez-vous",
	                    "Rendez vous créé avec succès"
	                );


	           // resetForm(); // optionnel mais recommandé
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(
	                    FacesMessage.SEVERITY_ERROR,
	                    "Erreur",
	                    "Échec de la création du rendez-vous"
	                )
	            );
	            UiMessage.swalError(
	                    "Gestion des rendez-vous",
	                    "Échec de la création du rendez-vous"
	                );
	        }

	    } catch (Exception e) {
	        FacesContext.getCurrentInstance().addMessage(null,
	            new FacesMessage(
	                FacesMessage.SEVERITY_FATAL,
	                "Erreur système",
	                "Une erreur inattendue est survenue"
	            )
	        );
	    }
	}

 public void retirerListeParticipantRdv(Demandeur dem){
	 System.out.println("debut:"+dem.getPersonne().getNom());
     if (listeParticipReelRdv.contains(dem)) {
    	 listeParticipReelRdv.remove(dem);
    	 System.out.println("enter:"+dem.getPersonne().getNom());
     }  
   
}
 

	public int getTotalRendezvous() {
	    return listeRendeVous != null ? listeRendeVous.size() : 0;
	}

	public long getNbInities() {
	    return listeRendeVous.stream().filter(e -> e.getEtatRdv() == 0).count();
	}

	public long getNbClotures() {
	    return listeRendeVous.stream().filter(e -> e.getEtatRdv() == 1).count();
	}

	public long getNbAnnules() {
	    return listeRendeVous.stream().filter(e -> e.getEtatRdv() == 2).count();
	}
 
 public void annuler(RendeVous rdv) {
	 rdv.setEtatRdv(2);
	 
	 
 }
    
    
    public String versRendezVous() {
    	
    	listeRendeVous = rendezVousService.findAll();
    	return "/secured/rdv/listeEvent.xhtml?faces-redirect=true";
    }
    
    
    public String versCreateRendezVous() {
    	 
    	return "/secured/rdv/createRdv.xhtml?faces-redirect=true";
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public ScheduleEvent<?> getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(ScheduleEvent<?> selectedEvent) {
        this.selectedEvent = selectedEvent;
    }


	public List<RendeVous> getListeRendeVous() {
		return listeRendeVous;
	}


	public void setListeRendeVous(List<RendeVous> listeRendeVous) {
		this.listeRendeVous = listeRendeVous;
	}


	public RendeVous getSelectedRdv() {
		return selectedRdv;
	}


	public void setSelectedRdv(RendeVous selectedRdv) {
		this.selectedRdv = selectedRdv;
	}


	public String getObjectif() {
		return objectif;
	}


	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}


	public LocalDateTime getDateRdv() {
		return dateRdv;
	}


	public void setDateRdv(LocalDateTime dateRdv) {
		this.dateRdv = dateRdv;
	}


	public List<Demandeur> getListeParticipRdv() {
		return listeParticipRdv;
	}


	public void setListeParticipRdv(List<Demandeur> listeParticipRdv) {
		this.listeParticipRdv = listeParticipRdv;
	}




	public List<Demandeur> getListeParticipReelRdv() {
		return listeParticipReelRdv;
	}


	public void setListeParticipReelRdv(List<Demandeur> listeParticipReelRdv) {
		this.listeParticipReelRdv = listeParticipReelRdv;
	}


	public String getLieu() {
		return lieu;
	}


	public void setLieu(String lieu) {
		this.lieu = lieu;
	}


	public Date getHeureFinRdv() {
		return heureFinRdv;
	}


	public void setHeureFinRdv(Date heureFinRdv) {
		this.heureFinRdv = heureFinRdv;
	}


	public Date getHeureDebutRdv() {
		return heureDebutRdv;
	}


	public void setHeureDebutRdv(Date heureDebutRdv) {
		this.heureDebutRdv = heureDebutRdv;
	}


	public LoginBean getLoginBean() {
		return loginBean;
	}


	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}

}
