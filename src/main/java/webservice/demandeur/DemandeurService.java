package webservice.demandeur;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.entity.Demandeur;
import com.app.entity.Personne;

import java.util.List;
import java.util.stream.Collectors;

@Path("/demendeurs")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DemandeurService {

    @EJB
    private DemandeurDao demanadeurDao;

    @GET
    public List<DemandeurDTO> getAll() {
        return demanadeurDao.findAll()
                .stream()
                .map(DemandeurDTO::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        try {
        	Demandeur d = demanadeurDao.findById(id);

            if (d == null) {
                // Cas ressource non trouvée
                return Response.status(Response.Status.NOT_FOUND) // 404
                        .entity(new MessageDTO("Demandeur introuvable pour l'id " ,-1))
                        .build();
            }

            // Cas succès
            return Response.ok(new DemandeurDTO(d)).build(); // 200 OK

        } catch (IllegalArgumentException e) {
            // Cas d'argument invalide (ex: id null)
            return Response.status(Response.Status.BAD_REQUEST) // 400
                    .entity(new MessageDTO("ID invalide : " + e.getMessage(),-1))
                    .build();

        } catch (Exception e) {
            // Cas erreur serveur
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // 500
                    .entity(new MessageDTO("Erreur serveur : " + e.getMessage(),-1))
                    .build();
        }
    }

    @GET
    @Path("/autoemploi")
    @Produces(MediaType.APPLICATION_JSON)
    public List<DemandeurDTO> demandeurAutoEmploi() {
        return demanadeurDao.demandeurAutoEmploi()
                .stream()
                .map(DemandeurDTO::new)
                .collect(Collectors.toList());
    }
    


    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouter(DemandeurDTO dto) {
        try {
            // Validation simple
            if (dto.getPersonne() == null ) {
                return Response.status(Response.Status.BAD_REQUEST) // 400 HTTP
                        .entity(new MessageDTO("Nom et prénom sont obligatoires", -1))
                        .build();
            }

            // Création de l'entité
            Demandeur d = new Demandeur();
            d.setPersonne(dto.getPersonne());
            d.setSite(dto.getSite());
            d.setStatut(dto.getStatut());
            d.setUtilisateur(dto.getUtilisateur());

            // Sauvegarde en base
            demanadeurDao.save(d);

            // Tout s'est bien passé
            return Response.status(Response.Status.CREATED) // 201 HTTP
                    .entity(new MessageDTO("Demandeur ajouté avec succès", 1))
                    .build();

        } catch (Exception e) {
            // Erreur serveur ou autre problème
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // 500 HTTP
                    .entity(new MessageDTO("Erreur lors de l'ajout : " + e.getMessage(), -1))
                    .build();
        }
    }


}
