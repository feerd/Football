package football.service;


import football.data.DataHandler;
import football.model.Player;
import football.model.Team;

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
@Path("team")
public class TeamService {

    /**
     * produces a map of all teams
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBooks() {
        Map<String, Team> teamMap = DataHandler.getTeamMap();

        Response response = Response
                .status(200)
                .entity(teamMap)
                .build();

        return response;
    }

    /**
     * reads a single team identified by the teamId
     *
     * @param teamUUID identifier
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(@QueryParam("uuid") String teamUUID) {
        Player player = null;
        int httpStatus;

        try {
            UUID.fromString(teamUUID);
            player = DataHandler.readPlayer(teamUUID);
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
