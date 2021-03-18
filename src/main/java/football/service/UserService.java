package football.service;

import football.data.DataHandler;
import football.model.Player;
import football.model.User;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.UUID;

/**
 * service for users
 * <p>
 * Football
 *
 * @author Dawvid Feer
 */
@Path("user")
public class UserService {

    /**
     * reads a single user identified by the userUUID
     *
     * @param userUUID identifier
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(@QueryParam("uuid") String userUUID) {
        User user = null;
        int httpStatus;

        try {
            UUID.fromString(userUUID);
            user = DataHandler.readUser(userUUID);
            if (user.getUserName() == null) {
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argumentException) {
            httpStatus = 400;
        }


        Response response = Response
                .status(httpStatus)
                .entity(user)
                .build();

        return response;
    }
}