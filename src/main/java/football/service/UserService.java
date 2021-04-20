package football.service;

import football.data.DataHandler;
import football.data.UserData;
import football.model.User;
import org.w3c.dom.UserDataHandler;

import javax.annotation.PostConstruct;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
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
     * login the current user
     *
     * @param username
     * @param password
     * @return response object
     */
    @POST
    @Path("login")
    @Produces(MediaType.TEXT_PLAIN)
    public Response login(
            @FormParam("username") String username,
            @FormParam("password") String password

    ) {
        int httpStatus;

        User user = UserData.findUser(username, password);
        if (user == null || user.getRole() == null || user.getRole().equals("guest")) {
            httpStatus = 404;
        } else {
            httpStatus = 200;
        }

        NewCookie cookie = new NewCookie(
                "userRole",
                user.getRole(),
                "/",
                "",
                "Login-Cookie",
                600,
                false
        );


        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();

        return response;
    }

    /**
     * logout the current user
     *
     * @return response object
     */
    @DELETE
    @Path("logout")
    @Produces(MediaType.TEXT_PLAIN)
    public Response logout() {
        int httpStatus = 200;

        NewCookie cookie = new NewCookie(
                "userRole",
                "guest",
                "/",
                "",
                "Login-Cookie",
                1,
                false
        );
        Response response = Response
                .status(httpStatus)
                .entity("")
                .cookie(cookie)
                .build();

        return response;
    }


    /**
     * reads a single user identified by the userUUID
     *
     * @param userUUID identifier
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPlayer(@QueryParam("uuid") String userUUID) {
        User user = null;
        int httpStatus;

        try {
            UUID.fromString(userUUID);
            user = DataHandler.readUser(userUUID);
            if (user.getUsername() == null) {
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