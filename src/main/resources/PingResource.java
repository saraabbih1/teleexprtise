package ma.youcode.teleexpertise.resource;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;

@Path("/ping")
public class PingResource {

    @GET
    public String ping() {
        return "API OK";
    }
}