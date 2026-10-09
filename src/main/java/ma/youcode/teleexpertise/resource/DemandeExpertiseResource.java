package ma.youcode.teleexpertise.resource;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.youcode.teleexpertise.exception.NotFoundException;
import ma.youcode.teleexpertise.entity.DemandeExpertise;
import ma.youcode.teleexpertise.exception.DemandeExpertiseException;
import ma.youcode.teleexpertise.service.DemandeExpertiseService;
import ma.youcode.teleexpertise.dto.ErrorResponse;

@Path("/demandes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DemandeExpertiseResource {

    private final DemandeExpertiseService service;

    public DemandeExpertiseResource() {
        this.service = new DemandeExpertiseService();
    }

    @POST
    public Response create(DemandeExpertise demande) {

        try {

            DemandeExpertise created = service.create(demande);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(created)
                    .build();

        } catch (NotFoundException e) {

            return Response
                .status(Response.Status.NOT_FOUND)
                .entity(new ErrorResponse(e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();

        } catch (DemandeExpertiseException e) {

            return Response
                .status(Response.Status.BAD_REQUEST)
                .entity(new ErrorResponse(e.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
        }
    }
}