package webservice.personne;

import javax.ejb.EJB;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.app.entity.Personne;

import java.util.List;
import java.util.stream.Collectors;

@Path("/personnes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PersonneService {

    @EJB
    private PersonneDao personneDao;

    @GET
    public List<PersonneDTO> getAll() {
        return personneDao.findAll()
                .stream()
                .map(PersonneDTO::new)
                .collect(Collectors.toList());
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") Long id) {
        try {
            Personne p = personneDao.findById(id);

            if (p == null) {
                // Cas ressource non trouvée
                return Response.status(Response.Status.NOT_FOUND) // 404
                        .entity(new MessageDTO("Personne introuvable pour l'id " ,-1))
                        .build();
            }

            // Cas succès
            return Response.ok(new PersonneDTO(p)).build(); // 200 OK

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



    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response ajouter(PersonneDTO dto) {
        try {
            // Validation simple
            if (dto.getNom() == null || dto.getPrenom() == null) {
                return Response.status(Response.Status.BAD_REQUEST) // 400 HTTP
                        .entity(new MessageDTO("Nom et prénom sont obligatoires", -1))
                        .build();
            }

            // Création de l'entité
            Personne p = new Personne();
            p.setNom(dto.getNom());
            p.setPrenom(dto.getPrenom());
            p.setDateNaissance(dto.getDateNaissance());
            p.setSexe(dto.getSexe());
            p.setEmail(dto.getEmail());
            p.setTelephone(dto.getTelephone());

            // Sauvegarde en base
            personneDao.save(p);

            // Tout s'est bien passé
            return Response.status(Response.Status.CREATED) // 201 HTTP
                    .entity(new MessageDTO("Personne ajoutée avec succès", 1))
                    .build();

        } catch (Exception e) {
            // Erreur serveur ou autre problème
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR) // 500 HTTP
                    .entity(new MessageDTO("Erreur lors de l'ajout : " + e.getMessage(), -1))
                    .build();
        }
    }


}
