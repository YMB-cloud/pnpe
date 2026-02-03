package com.app.contrat;

import java.awt.Window.Type;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleModel;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

import com.app.auth.LoginBean;
import com.app.auth.UiMessage;
import com.app.entity.Contrat;
import com.app.entity.Demandeur;
import com.app.entity.Dispositif;
import com.app.entity.Evenement;
import com.app.entity.Morale;
import com.app.entity.NoteContrat;
import com.app.entity.Partenaire;
import com.app.entity.PartenaireEvenement;
import com.app.entity.PersonnelFonctionDemandeur;
import com.app.entity.PersonnelFonctionDemandeurEvenement;
import com.app.entity.RendeVous;
import com.app.entity.RendezVousParticipant;
import com.app.entity.RoleDTO;
import com.app.entity.TypeContrat;
import com.app.entity.TypeEvenement;
import com.app.evenement.EvenementService;

@Named
@SessionScoped
public class ContratBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Inject
    private ContratService contratService; // service pur
    
    @Inject
    private LoginBean loginBean; // service pur
    
    
    private Demandeur demandeur;
    
    private Morale morale;
    
    private TypeContrat typeContrat;
    
    private Contrat contratCapte;
    
    private BigDecimal  salaire;
    
    private LocalDate datedebut;
    
    private LocalDate dateFin;
    
    private String observationNote;
    
    private boolean afficherDates; 
    private List<Demandeur> listeDemandeur;
    private List<Morale> listeMorale;
    
    private List<Morale> listeMoraleChoisie;
    
    private List<Demandeur> listeDemandeurChoisi;
    
    private List<TypeContrat> listeTypeContrat;
    
    private List<Contrat> listeContrat;
    
    private TypeContrat selectedTypeContrat; // lié à value
    
    public String versContrat() {
   	 
        
   	return "/secured/contrat/listeContrat.xhtml?faces-redirect=true";
   }
    

    private TimelineModel<String, Date> timelineModel;
    



    public TimelineModel<String, Date> getTimelineModel() {
        return timelineModel;
    }
    
    
    
	@PostConstruct
      public void init() {
		listeDemandeur= new ArrayList<>(); 
		listeDemandeur=contratService.listeDemandeur();
		listeMorale= new ArrayList<>(); 
		listeMorale=contratService.listeMorale();
		listeMoraleChoisie=new ArrayList<>();
		listeDemandeurChoisi=new ArrayList<>();
		listeTypeContrat=new ArrayList<>();
		listeTypeContrat=contratService.listeTypeContrat();
		listeContrat=contratService.listeContrat();
		
		
    }

	public void typeContratChanged() {
	    if(typeContrat != null && !"CDD".equals(typeContrat.getCode())) {
	        
	        afficherDates = true;
	    } else {
	        afficherDates = false;
	    }
	}

	public void contratCapteVoulu(Contrat cntr) {
		 
        observationNote=null;
		contratCapte =contratService.contratCapte(cntr);
		afficherDates=false;
	            
	}
	public Demandeur getDemandeur() {
		return demandeur;
	}


	public void setDemandeur(Demandeur demandeur) {
		this.demandeur = demandeur;
	}


	public Morale getMorale() {
		return morale;
	}


	public void setMorale(Morale morale) {
		this.morale = morale;
	}


	public TypeContrat getTypeContrat() {
		return typeContrat;
	}


	public void setTypeContrat(TypeContrat typeContrat) {
		this.typeContrat = typeContrat;
	}


	public BigDecimal getSalaire() {
		return salaire;
	}


	public void setSalaire(BigDecimal salaire) {
		this.salaire = salaire;
	}


	public List<Demandeur> getListeDemandeurChoisi() {
		return listeDemandeurChoisi;
	}


	public void setListeDemandeurChoisi(List<Demandeur> listeDemandeurChoisi) {
		this.listeDemandeurChoisi = listeDemandeurChoisi;
	}


	public LocalDate getDatedebut() {
		return datedebut;
	}


	public void setDatedebut(LocalDate datedebut) {
		this.datedebut = datedebut;
	}


	public List<Morale> getListeMorale() {
		return listeMorale;
	}


	public void setListeMorale(List<Morale> listeMorale) {
		this.listeMorale = listeMorale;
	}


	public List<Demandeur> getListeDemandeur() {
		return listeDemandeur;
	}


	public void setListeDemandeur(List<Demandeur> listeDemandeur) {
		this.listeDemandeur = listeDemandeur;
	}


	public LocalDate getDateFin() {
		return dateFin;
	}


	public void setDateFin(LocalDate dateFin) {
		this.dateFin = dateFin;
	}


	public List<TypeContrat> getListeTypeContrat() {
		return listeTypeContrat;
	}


	public void setListeTypeContrat(List<TypeContrat> listeTypeContrat) {
		this.listeTypeContrat = listeTypeContrat;
	}


	public List<Contrat> getListeContrat() {
		return listeContrat;
	}


	public void setListeContrat(List<Contrat> listeContrat) {
		this.listeContrat = listeContrat;
	}


	public List<Morale> getListeMoraleChoisie() {
		return listeMoraleChoisie;
	}


	public void setListeMoraleChoisie(List<Morale> listeMoraleChoisie) {
		this.listeMoraleChoisie = listeMoraleChoisie;
	}


	public Contrat getContratCapte() {
		return contratCapte;
	}


	public void setContratCapte(Contrat contratCapte) {
		this.contratCapte = contratCapte;
	}


	public LoginBean getLoginBean() {
		return loginBean;
	}


	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}


	public TypeContrat getSelectedTypeContrat() {
		return selectedTypeContrat;
	}



	public void setSelectedTypeContrat(TypeContrat selectedTypeContrat) {
		this.selectedTypeContrat = selectedTypeContrat;
	}



	public String getObservationNote() {
		return observationNote;
	}


	public void setObservationNote(String observationNote) {
		this.observationNote = observationNote;
	}


	



	public boolean isAfficherDates() {
		return afficherDates;
	}



	public void setAfficherDates(boolean afficherDates) {
		this.afficherDates = afficherDates;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}
    
	 public boolean demandeurDejaAjouter(Demandeur dem) {
		    if (dem == null || dem.getId() == null) {
		        return false;
		    }

		    for (Demandeur x : listeDemandeurChoisi) {
		        if (x.getId() != null && x.getId().equals(dem.getId())) {
		            return true;
		        }
		    }
		    return false;
		}
	 
	 public void ajouterDemandeur(Demandeur dem) {

		    if (listeDemandeurChoisi.size()==0) {

		    	listeDemandeurChoisi.add(dem);
		        

		        // Tri alphabétique par NOM (insensible à la casse)
		    	listeDemandeurChoisi.sort(
		            Comparator.comparing(
		                p -> p.getPersonne().getNom(),
		                String.CASE_INSENSITIVE_ORDER
		            )
		        );
		    }
		    else {
		    	UiMessage.swalError(
	                    "Gestion des contrats",
	                    "Un demandeur par contrat"
	                );
		    }
		}
	 
	 public void ajouterMorale(Morale dem) {
		 
		    if (listeMoraleChoisie.size()==0) {

		    	listeMoraleChoisie.add(dem);

		        // Tri alphabétique par NOM (insensible à la casse)
		    	listeMoraleChoisie.sort(
		            Comparator.comparing(
		                p -> p.getRaisonSociale(),
		                String.CASE_INSENSITIVE_ORDER
		            )
		        );
		    }
		    else {
		    	UiMessage.swalError(
	                    "Gestion des contrats",
	                    "Un employeur par contrat"
	                );
		    }
		}
	 
	 public void retirerDemandeur(Demandeur obj) {
		    listeDemandeurChoisi.removeIf(x ->
		        x.getId().equals(obj.getId())
		    );
		}
	 public boolean moraleDejaAjouter(Morale dem) {
		    if (dem == null || dem.getId() == null) {
		        return false;
		    }

		    for (Morale x : listeMoraleChoisie) {
		        if (x.getId() != null && x.getId().equals(dem.getId())) {
		            return true;
		        }
		    }
		    return false;
		}
	 
	 public void retirerMorale(Morale obj) {
		    listeMoraleChoisie.removeIf(x ->
		        x.getId().equals(obj.getId())
		    );
		}
  
	 public void creeContrat() {

		   try {
			   
			  if(typeContrat==null) {
				  UiMessage.swalError(
		                    "Gestion des contrat",
		                    "Selectionnez un type de contrat"
		                );
				  return;
			  }
			  if(listeDemandeurChoisi.size()==0) {
				  UiMessage.swalError(
		                    "Gestion des conterats",
		                    "Aucun demandeur renseigné"
		                );
				  return;
			  }
			  
			  if(listeMoraleChoisie.size()==0) {
				  UiMessage.swalError(
		                    "Gestion des conterats",
		                    "Aucun employeur selectionné"
		                );
				  return;
			  }
			  demandeur=listeDemandeurChoisi.get(0);
			  morale=listeMoraleChoisie.get(0);
			   Contrat contrat = new Contrat();
			   contrat.setDatedebut(datedebut);
			   contrat.setDateFin(dateFin);
			   contrat.setSalaire(salaire);
			   contrat.setDemandeur(demandeur);
			   contrat.setMorale(morale);
			   contrat.setTypeContrat(typeContrat);
		

		        boolean success = contratService.creerContrat(contrat);

		        if (success) {
		        	init();
					/*
					 * FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					 * FacesMessage.SEVERITY_INFO, "Succès", "L'evenement a été créé avec succès" )
					 * 
					 * );
					 */
		            UiMessage.swalSuccessAndReload(
		                    "Gestion des contrat",
		                    "Contrat  créé avec succès"
		                );


		           // resetForm(); // optionnel mais recommandé
		        } else {
		            FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage(
		                    FacesMessage.SEVERITY_ERROR,
		                    "Erreur",
		                    "Échec de la création du contrat"
		                )
		            );
		            UiMessage.swalError(
		                    "Gestion des contrat",
		                    "Échec de la création du contrat"
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
		        UiMessage.swalError(
	                    "Gestion des contrats",
	                    "Une erreur inattendue est survenue"
	                );
		    }
		}
	 
	 
	 
	 
	 public void creeNoteContrat() {

		   try {
			   
			  if(observationNote==null || observationNote.isEmpty()) {
				  UiMessage.swalError(
		                    "Gestion des contrat",
		                    "Saisir la note"
		                );
				  return;
			  }
			  
			  
			  NoteContrat noteContrat = new NoteContrat();
			  noteContrat.setDateNote(LocalDate.now());
			  noteContrat.setContrat(contratCapte);
			  noteContrat.setObservation(observationNote);
			  noteContrat.setUtilisateur(loginBean.getConnectedUser());
		

		        boolean success = contratService.creerNoteContrat(noteContrat);

		        if (success) {
		        	init();
					/*
					 * FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
					 * FacesMessage.SEVERITY_INFO, "Succès", "L'evenement a été créé avec succès" )
					 * 
					 * );
					 */
		            UiMessage.swalSuccessAndReload(
		                    "Gestion des contrat",
		                    "Note ajouté au contrat  avec succès"
		                );


		           // resetForm(); // optionnel mais recommandé
		        } else {
		            FacesContext.getCurrentInstance().addMessage(null,
		                new FacesMessage(
		                    FacesMessage.SEVERITY_ERROR,
		                    "Erreur",
		                    "Échec de la création du contrat"
		                )
		            );
		            UiMessage.swalError(
		                    "Gestion des contrat",
		                    "Échec de la création de la note du contrat"
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
		        UiMessage.swalError(
	                    "Gestion des contrats",
	                    "Une erreur inattendue est survenue"
	                );
		    }
		}
	
}
