package com.app.contrat;

import java.io.Serializable;
import java.sql.Time;
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

@Named
@SessionScoped
public class ContratBean implements Serializable {

    private static final long serialVersionUID = 1L;
    
    
    

    public String versContrat() {
   	 
        
   	return "/secured/contrat/listeContrat.xhtml?faces-redirect=true";
   }
    

	@PostConstruct
      public void init() {
		
    }
    
	
	
	
	
}
