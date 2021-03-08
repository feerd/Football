package football.service;


import football.data.DataHandler;
import football.model.Player;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;


/**
 * provides services for football
 * <p>
 * Football
 *
 * @author David Feer
 */
@Path("player")
public class PlayerService {

    /**
     * produces a map of all books
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks() {
        Map<String, Player> playerMap = DataHandler.getPlayerMap();

        Response response = Response
                .status(200)
                .entity(playerMap)
                .build();

        return response;
    }

    /**
     * reads a single book identified by the bookId
     *
     * @param bookUUID
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(@QueryParam("uuid") String bookUUID) {
        Player player = null;
        int httpStatus;

        try {
            UUID.fromString(bookUUID);
            player = DataHandler.readPlayer(bookUUID);
            if (player.getName() == null) {
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argumentException) {
            httpStatus = 400;
        }


        Response response = Response
                .status(httpStatus)
                .entity(player)
                .build();

        return response;
    }
}
