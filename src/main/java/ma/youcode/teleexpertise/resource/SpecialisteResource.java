package ma.youcode.teleexpertise.resource;

import java.util.List;

import jakarta.annotation.security.RolesAllowed;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ma.youcode.teleexpertise.entity.Specialiste;
import ma.youcode.teleexpertise.entity.Specialite;
import ma.youcode.teleexpertise.service.SpecialisteService;


@Path("/specialistes")
@Produces(MediaType.APPLICATION_JSON)
@RolesAllowed("SPECIALISTE")
public class SpecialisteResource {

    private final SpecialisteService service = new SpecialisteService();

    @GET
    public Response getSpecialistes(@QueryParam("specialite") String specialite) {

        Specialite specialiteEnum;

        try {
            specialiteEnum = Specialite.valueOf(specialite.toUpperCase());
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Spécialité invalide")
                    .build();
        }

        List<Specialiste> specialistes =
                service.getSpecialistes(specialiteEnum);

        return Response.ok(specialistes).build();
    }
}