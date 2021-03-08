package football.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * service for testing
 * <p>
 * Football
 *
 * @author David Feer
 */
@Path("test")
public class TestService {

    /**
     * test service
     *
     * @return Response
     */
    @GET
    @Path("test")
    @Produces(MediaType.TEXT_PLAIN)
    public Response test() {

        return Response
                .status(200)
                .entity("Erfolgreich")
                .build();
    }
}