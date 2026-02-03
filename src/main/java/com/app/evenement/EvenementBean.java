package com.app.evenement;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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

import com.app.auth.LoginBean;
import com.app.auth.UiMessage;
import com.app.entity.Demandeur;
import com.app.entity.Dispositif;
import com.app.entity.Evenement;
import com.app.entity.Partenaire;
import com.app.entity.PartenaireEvenement;
import com.app.entity.PersonnelFonctionDemandeur;
import com.app.entity.PersonnelFonctionDemandeurEvenement;
import com.app.entity.RendeVous;
import com.app.entity.RendezVousParticipant;
import com.app.entity.RoleDTO;
import com.app.entity.TypeEvenement;
import com.app.notification.NotificationBean;

@Named
@SessionScoped
public class EvenementBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private EvenementService evenementService; // service pur
    @Inject
    private NotificationBean notificationBean; // service pur
    @Inject
    private LoginBean loginBean; // service pur
    private ScheduleModel eventModel;
    private List<Evenement> listeEvenement;
    private ScheduleEvent<?> selectedEvent;
    private Evenement selectedEv;
    private Evenement evenementDetailCapte;
    private Evenement evenementUpdate;
    private List<TypeEvenement> listeTypeEvenement;
    private TypeEvenement typeEvenementSeleced;
    private List<Partenaire> listePartenaire;
    private List<Partenaire> listePartenaireConvie;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private LocalDateTime dateEven;
    
    private Dispositif dispositifChoisi;
    private List<Dispositif> listeDispositif;
    private String lienVisio;
    private String titre;
    
	private List<PartenaireEvenement> listePartenaireEvenement;
	private List<PersonnelFonctionDemandeurEvenement> listeDemandeurEvenement;
    private String lieu;
    private String objectif;
    private List<PersonnelFonctionDemandeur> listeDemandeur;
    private List<PersonnelFonctionDemandeur> listeDemandeurConvies;
    
    

	@PostConstruct
    public void init() {
		listeEvenement = evenementService.findAll();
		listePartenaire = evenementService.findAllPartenaire();
		listeDemandeur = evenementService.findAllDemandeur();
		listeDispositif=evenementService.listeDispositif();
		 evenementDetailCapte = new Evenement(); // évite le null
         
		 if (evenementUpdate == null) {
		        evenementUpdate = new Evenement(); // objet vide pour le rendu
		    }
		 listeDemandeurConvies=new ArrayList<>();
		 listePartenaireConvie=new ArrayList<>(); 
		 /*
		 * evenementDetail.setPersonnelsDemandeur(new ArrayList<>());
		 * 
		 * evenementDetail.setPartenaires(new ArrayList<>());
		 */
		listeTypeEvenement=evenementService.findAllTypeEvenement();
		
		
        eventModel = new DefaultScheduleModel();
        for (Evenement rv : listeEvenement) {
            DefaultScheduleEvent<?> event =
                    DefaultScheduleEvent.builder()
                        .id(rv.getId().toString())
                        .title(rv.getTitre())
                        .description(rv.getDescription())
                        .startDate(rv.getDateDebut())
                        .endDate(rv.getDateFin())
                        .build();
            eventModel.addEvent(event);
        }
    }
    
	
	
	
	
	public void evenementCapte(Evenement ev) {

		evenementDetailCapte =evenementService.evenementVoulu(ev);
		listePartenaireEvenement=evenementService.partenairesEvenement(ev);
		listeDemandeurEvenement=evenementService.demandeurEvenement(ev);
		
		        
	}
	
	
	
	public void annuleEvenement(Evenement ev) {

		if(evenementService.changeEtatEvenement(ev,2)) {
			UiMessage.swalSuccessAndReload(
                    "Gestion des evenements",
                    "L'evenement annulée avec succès"
                );
		}
		else {
			UiMessage.swalError(
                    "Gestion des evenements",
                    "Echec lors de l'annulation de l'evenement"
                );
		}
		
		        
	}
	
	public void clotureEvenement(Evenement ev) {

		if(evenementService.changeEtatEvenement(ev,1)) {
			UiMessage.swalSuccessAndReload(
                    "Gestion des evenements",
                    "L'evenement clôturé avec succès"
                );
		}
		else {
			UiMessage.swalError(
                    "Gestion des evenements",
                    "Echec lors de la clôture de l'evenement"
                );
		}
		
		        
	}
	
	public int getTotalEvenements() {
	    return listeEvenement != null ? listeEvenement.size() : 0;
	}

	public long getNbInities() {
	    return listeEvenement.stream().filter(e -> e.getEtat() == 0).count();
	}

	public long getNbClotures() {
	    return listeEvenement.stream().filter(e -> e.getEtat() == 1).count();
	}

	public long getNbAnnules() {
	    return listeEvenement.stream().filter(e -> e.getEtat() == 2).count();
	}

	    
  
 public void ajouterDemandeur(PersonnelFonctionDemandeur dem) {

	    System.out.println("debut : " + dem.getDemandeur().getPersonne().getNom());

	    if (!listeDemandeurConvies.contains(dem)) {

	        listeDemandeurConvies.add(dem);
	        

	        // Tri alphabétique par NOM (insensible à la casse)
	        listeDemandeurConvies.sort(
	            Comparator.comparing(
	                p -> p.getDemandeur().getPersonne().getNom(),
	                String.CASE_INSENSITIVE_ORDER
	            )
	        );
	    }
	}
 public boolean demandeurDejaAjouter(PersonnelFonctionDemandeur dem) {
	    if (dem == null || dem.getId() == null) {
	        return false;
	    }

	    for (PersonnelFonctionDemandeur x : listeDemandeurConvies) {
	        if (x.getId() != null && x.getId().equals(dem.getId())) {
	            return true;
	        }
	    }
	    return false;
	}
 
 public boolean partenaireDejaAjouter(Partenaire dem) {
	    if (dem == null || dem.getId() == null) {
	        return false;
	    }

	    for (Partenaire x : listePartenaireConvie) {
	        if (x.getId() != null && x.getId().equals(dem.getId())) {
	            return true;
	        }
	    }
	    return false;
	}


	 


 public void ajouterPartenaire(Partenaire dem){
	 System.out.println("debut:"+dem.getPersonne().getNom());
         if (!listePartenaireConvie.contains(dem)) {
        	 listePartenaireConvie.add(dem);
        	 listePartenaireConvie.sort(
        	            Comparator.comparing(
        	                p -> p.getPersonne().getNom(),
        	                String.CASE_INSENSITIVE_ORDER
        	            )
        	        );
        	
         }  
	   
 }


 public void retirerDemandeur(PersonnelFonctionDemandeur obj) {
	    listeDemandeurConvies.removeIf(x ->
	        x.getId().equals(obj.getId())
	    );
	}


 public void retirerPartenaire(Partenaire dem){
	 System.out.println("debut:"+dem.getPersonne().getNom());
         if (listePartenaireConvie.contains(dem)) {
        	 listePartenaireConvie.remove(dem);
         }   
 }

 public void confirmCreeRdv() {
	    // Rien ici, la confirmation est gérée côté JS/Swal
	}
 public void reset(){
	 
	   
 }
 
 
 public void creeEvenement() {

	   try {
		   
		  if(dispositifChoisi==null) {
			  UiMessage.swalError(
	                    "Gestion des evenements",
	                    "Selectionnez un dispositif"
	                );
			  return;
		  }
		  if(listeDemandeurConvies.size()==0) {
			  UiMessage.swalError(
	                    "Gestion des evenements",
	                    "Aucun demandeur renseigné"
	                );
			  return;
		  }
		   Evenement evenement = new Evenement();
	        
		    evenement.setDispositifEvenement(dispositifChoisi);
		
		                 
	        evenement.setDateCreation(LocalDateTime.now());
	        evenement.setDateDebut(dateDebut);
	        evenement.setDateFin(dateFin);
	        evenement.setEtat(0);
	        evenement.setLieu(lieu);
	        evenement.setObjectif(objectif);
	        evenement.setUtilisateur(loginBean.getConnectedUser());
	        //evenement.setTypeEvenement(typeEvenementSeleced);

	        boolean success = evenementService.creerEvenement(
	        		evenement,
	        		listeDemandeurConvies,
	        		listePartenaireConvie	
	        );

	        if (success) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(
	                    FacesMessage.SEVERITY_INFO,
	                    "Succès",
	                    "L'evenement a été créé avec succès"
	                )
	                
	            );
	            UiMessage.swalSuccessAndReload(
	                    "Gestion des evenements",
	                    "L'evenement créé avec succès"
	                );


	           // resetForm(); // optionnel mais recommandé
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(
	                    FacesMessage.SEVERITY_ERROR,
	                    "Erreur",
	                    "Échec de la création de l'evenement"
	                )
	            );
	            UiMessage.swalError(
	                    "Gestion des rendez-vous",
	                    "Échec de la création de l'evenement"
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
                    "Gestion des rendez-vous",
                    "Une erreur inattendue est survenue"
                );
	    }
	}
 public void updateEvenement() {

	   try {
		    
			/*
			 * evenementUpdate.setDateDebut(dateDebut); evenementUpdate.setDateFin(dateFin);
			 * evenementUpdate.setLieu(lieu); evenementUpdate.setObjectif(objectif);
			 */
	        //evenement.setTypeEvenement(typeEvenementSeleced);

	        boolean success = evenementService.updateEvenement(
	        		evenementUpdate,
	        		listeDemandeurConvies,
	        		listePartenaireConvie	
	        );

	        if (success) {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(
	                    FacesMessage.SEVERITY_INFO,
	                    "Succès",
	                    "L'evenement a été modifié avec succès"
	                )
	                
	            );
	            
	           
	            
	          
	            UiMessage.swalSuccessAndReload(
	                    "Gestion des evenements",
	                    "L'evenement modifié avec succès"
	                );


	           // resetForm(); // optionnel mais recommandé
	        } else {
	            FacesContext.getCurrentInstance().addMessage(null,
	                new FacesMessage(
	                    FacesMessage.SEVERITY_ERROR,
	                    "Erreur",
	                    "Échec de la création de l'evenement"
	                )
	            );
	            UiMessage.swalError(
	                    "Gestion des rendez-vous",
	                    "Échec de la création de l'evenement"
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
                  "Gestion des rendez-vous",
                  "Une erreur inattendue est survenue"
              );
	    }
	}


 
 public void annuler(RendeVous rdv) {
	 rdv.setEtatRdv(2);
	 
	 
 }
 
 
 public void versPopUpdate(Evenement ev) {
	    evenementUpdate = evenementService.findAvecRelations(ev.getId()); // toutes les collections chargées
	    dispositifChoisi = evenementUpdate.getDispositifEvenement();
	    System.out.println("evenementUpdate.getDispositifEvenement()"+evenementUpdate.getDispositifEvenement());

	    listePartenaireConvie = evenementUpdate.getPartenaires().stream()
	        .filter(pe -> pe.getStatut() == 1)
	        .map(PartenaireEvenement::getPartenaire)
	        .collect(Collectors.toList());

	    listeDemandeurConvies = evenementUpdate.getPersonnelsDemandeur().stream()
	        .filter(pd -> pd.getStatut() == 1)
	        .map(PersonnelFonctionDemandeurEvenement::getPersonnelFonctionDemandeur)
	        .collect(Collectors.toList());
	}

 
 public String formatDateFr(String dateStr) {
	    if (dateStr == null || dateStr.trim().isEmpty()) {
	        return "";
	    }

	    try {
	        Date date;

	        if (dateStr.contains("T")) {
	            // Cas : 2026-01-07T00:00
	            SimpleDateFormat inputFormat =
	                    new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
	            date = inputFormat.parse(dateStr);
	        } else {
	            // Cas : 2026-02-27
	            SimpleDateFormat inputFormat =
	                    new SimpleDateFormat("yyyy-MM-dd");
	            date = inputFormat.parse(dateStr);
	        }

	        SimpleDateFormat outputFormat =
	                new SimpleDateFormat("dd/MM/yyyy");
	        return outputFormat.format(date);

	    } catch (ParseException e) {
	        return dateStr; // fallback propre
	    }
	}

 
 /*public void versPopUpdate(Evenement ev) {

	  System.out.println("ev.getId()" +ev.getId());
	 
	 evenementUpdate = evenementService.findAvecRelations(ev.getId());
	 dispositifChoisi = evenementUpdate.getDispositifEvenement();	
			 //evenementService.findAvecRelations(ev.getId());;
	
	 System.out.println("evenementUpdate.getLieu()" +evenementUpdate.getId());
	    

	    listePartenaireConvie = new ArrayList<>();
	    listeDemandeurConvies = new ArrayList<>();

	    for (PartenaireEvenement pe : evenementUpdate.getPartenaires()) {
	    	if(pe.getStatut()==1) {
	    		listePartenaireConvie.add(pe.getPartenaire());
	    	}
	        
	    }

	    for (PersonnelFonctionDemandeurEvenement pd : evenementUpdate.getPersonnelsDemandeur()) {
	    	 if(pd.getStatut()==1) {
	    		 listeDemandeurConvies.add(pd.getPersonnelFonctionDemandeur()); 
	    	 }
	        
	    }

	}*/
 
 
 public String versEvenementUpdate(Evenement ev) {

	    evenementUpdate = evenementService.findAvecRelations(ev.getId());
	    

	    listePartenaireConvie = new ArrayList<>();
	    listeDemandeurConvies = new ArrayList<>();

	    for (PartenaireEvenement pe : evenementUpdate.getPartenaires()) {
	    	if(pe.getStatut()==1) {
	    		listePartenaireConvie.add(pe.getPartenaire());
	    	}
	        
	    }

	    for (PersonnelFonctionDemandeurEvenement pd : evenementUpdate.getPersonnelsDemandeur()) {
	    	 if(pd.getStatut()==1) {
	    		 listeDemandeurConvies.add(pd.getPersonnelFonctionDemandeur()); 
	    	 }
	        
	    }

	    return "/secured/evenement/updateEvenement.xhtml?faces-redirect=true";
	}

    
    public String versEvenement() {
    	 listePartenaireConvie = new ArrayList<>();
 	     listeDemandeurConvies = new ArrayList<>();
 	     
 	     
         
    	return "/secured/evenement/createEvenement.xhtml?faces-redirect=true";
    }
    
    public String versEvenementRapport(Evenement ev) {
    	
    	
    	listeDemandeurEvenement = new ArrayList<>();
    	listePartenaireEvenement = new ArrayList<>();
    	evenementUpdate = evenementService.findAvecRelations(ev.getId()); // toutes les collections chargées
	 

    	listePartenaireEvenement = evenementUpdate.getPartenaires();
	        
    	listeDemandeurEvenement = evenementUpdate.getPersonnelsDemandeur();
	    
	   
        
   	return "/secured/evenement/rapportEvenement.xhtml?faces-redirect=true";
   }
    
    public void  nouvelEvenement() {
   	 listePartenaireConvie = new ArrayList<>();
	     listeDemandeurConvies = new ArrayList<>();
        
   
   }
   
    
// liste des evenements....
    
    public String versListeEvenement() {
    	 
    	return "/secured/evenement/listeEvenement.xhtml?faces-redirect=true";  	  	
    }
    
    public String versManageEvenements() {
   	 
    	return "/secured/evenement/manageEvenements.xhtml?faces-redirect=true";  	  	
    }
// *********************************
    
    
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


	

	
	

	

	




















	









	public String getObjectif() {
		return objectif;
	}


	public void setObjectif(String objectif) {
		this.objectif = objectif;
	}



	public String getLieu() {
		return lieu;
	}


	public void setLieu(String lieu) {
		this.lieu = lieu;
	}


	public List<PartenaireEvenement> getListePartenaireEvenement() {
		return listePartenaireEvenement;
	}


	public void setListePartenaireEvenement(List<PartenaireEvenement> listePartenaireEvenement) {
		this.listePartenaireEvenement = listePartenaireEvenement;
	}


	public List<Evenement> getListeEvenement() {
		return listeEvenement;
	}


	public void setListeEvenement(List<Evenement> listeEvenement) {
		this.listeEvenement = listeEvenement;
	}


	public List<Partenaire> getListePartenaie() {
		return listePartenaire;
	}


	public void setListePartenaie(List<Partenaire> listePartenaire) {
		this.listePartenaire = listePartenaire;
	}


	public List<Partenaire> getListePartenaire() {
		return listePartenaire;
	}


	public void setListePartenaire(List<Partenaire> listePartenaire) {
		this.listePartenaire = listePartenaire;
	}


	public LocalDateTime getDateEven() {
		return dateEven;
	}


	public void setDateEven(LocalDateTime dateEven) {
		this.dateEven = dateEven;
	}


	public Evenement getSelectedEv() {
		return selectedEv;
	}


	public void setSelectedEv(Evenement selectedEv) {
		this.selectedEv = selectedEv;
	}


	public List<PersonnelFonctionDemandeur> getListeDemandeur() {
		return listeDemandeur;
	}


	public void setListeDemandeur(List<PersonnelFonctionDemandeur> listeDemandeur) {
		this.listeDemandeur = listeDemandeur;
	}


	


	



	public List<PersonnelFonctionDemandeur> getListeDemandeurConvies() {
		return listeDemandeurConvies;
	}


	public void setListeDemandeurConvies(List<PersonnelFonctionDemandeur> listeDemandeurConvies) {
		this.listeDemandeurConvies = listeDemandeurConvies;
	}


	public List<Partenaire> getListePartenaireConvie() {
		return listePartenaireConvie;
	}


	public void setListePartenaireConvie(List<Partenaire> listePartenaireConvie) {
		this.listePartenaireConvie = listePartenaireConvie;
	}


	public LocalDateTime getDateDebut() {
		return dateDebut;
	}


	public void setDateDebut(LocalDateTime dateDebut) {
		this.dateDebut = dateDebut;
	}


	public LocalDateTime getDateFin() {
		return dateFin;
	}


	public void setDateFin(LocalDateTime dateFin) {
		this.dateFin = dateFin;
	}



	public List<TypeEvenement> getListeTypeEvenement() {
		return listeTypeEvenement;
	}



	public void setListeTypeEvenement(List<TypeEvenement> listeTypeEvenement) {
		this.listeTypeEvenement = listeTypeEvenement;
	}


	public TypeEvenement getTypeEvenementSeleced() {
		return typeEvenementSeleced;
	}


	public void setTypeEvenementSeleced(TypeEvenement typeEvenementSeleced) {
		this.typeEvenementSeleced = typeEvenementSeleced;
	}


	public List<PersonnelFonctionDemandeurEvenement> getListeDemandeurEvenement() {
		return listeDemandeurEvenement;
	}


	public void setListeDemandeurEvenement(List<PersonnelFonctionDemandeurEvenement> listeDemandeurEvenement) {
		this.listeDemandeurEvenement = listeDemandeurEvenement;
	}


	public Evenement getEvenementDetailCapte() {
		return evenementDetailCapte;
	}


	public void setEvenementDetailCapte(Evenement evenementDetailCapte) {
		this.evenementDetailCapte = evenementDetailCapte;
	}












	public Evenement getEvenementUpdate() {
		return evenementUpdate;
	}





	public void setEvenementUpdate(Evenement evenementUpdate) {
		this.evenementUpdate = evenementUpdate;
	}





	public String getLienVisio() {
		return lienVisio;
	}





	public void setLienVisio(String lienVisio) {
		this.lienVisio = lienVisio;
	}





	public String getTitre() {
		return titre;
	}





	public void setTitre(String titre) {
		this.titre = titre;
	}





	public Dispositif getDispositifChoisi() {
		return dispositifChoisi;
	}





	public void setDispositifChoisi(Dispositif dispositifChoisi) {
		this.dispositifChoisi = dispositifChoisi;
	}





	public List<Dispositif> getListeDispositif() {
		return listeDispositif;
	}





	public void setListeDispositif(List<Dispositif> listeDispositif) {
		this.listeDispositif = listeDispositif;
	}





	public NotificationBean getNotificationBean() {
		return notificationBean;
	}





	public void setNotificationBean(NotificationBean notificationBean) {
		this.notificationBean = notificationBean;
	}





	public LoginBean getLoginBean() {
		return loginBean;
	}





	public void setLoginBean(LoginBean loginBean) {
		this.loginBean = loginBean;
	}


	

	

	
	

	


	

}
