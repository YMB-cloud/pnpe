package com.app.contrat;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.app.entity.Demandeur;
import com.app.entity.Dispositif;
import com.app.entity.Evenement;
import com.app.entity.Partenaire;
import com.app.entity.PartenaireEvenement;
import com.app.entity.PersonnelFonctionDemandeur;
import com.app.entity.PersonnelFonctionDemandeurEvenement;
import com.app.entity.RendeVous;
import com.app.entity.RendezVousParticipant;
import com.app.entity.TypeEvenement;

@Stateless
public class ContratService {

    @PersistenceContext
    private EntityManager em;

}
