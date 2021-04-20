package football.service;


import football.data.DataHandler;
import football.model.Player;
import football.model.Team;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.ws.rs.*;
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
     * produces a map of all players
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listPlayers(
            @CookieParam("userRole") String userRole
    ) {
        Map<String, Player> playerMap = null;

        int httpStatus;
        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else if (userRole.equals("user") || userRole.equals("admin")) {
            httpStatus = 200;
            playerMap = DataHandler.getPlayerMap();
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity(playerMap)
                .build();

        return response;
    }

    /**
     * reads a single player identified by the playerId
     *
     * @param playerUUID identifier
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readPlayer(
            @QueryParam("uuid") String playerUUID,
            @CookieParam("userRole") String userRole

    ) {
        Player player = null;
        int httpStatus;

        if (userRole == null || userRole.equals("guest")) {
            httpStatus = 403;
        } else if (userRole.equals("user") || userRole.equals("admin")) {
            try {
                UUID.fromString(playerUUID);
                player = DataHandler.readPlayer(playerUUID);
                if (player.getName() == null) {
                    httpStatus = 404;
                } else {
                    httpStatus = 200;
                }
            } catch (IllegalArgumentException argumentException) {
                httpStatus = 400;
            }
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity(player)
                .build();

        return response;
    }

    /**
     * creates a new player
     *
     * @param name     the name of the player
     * @param teamUUID the unique key of the team
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createPlayer(
            @FormParam("name")
            @NotEmpty
            @Size(min = 2, max = 40)
                    String name,

            @FormParam("teamUUID")
            @NotEmpty
            @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String teamUUID,
            @CookieParam("userRole") String userRole

    ) {
        int httpStatus;
        if (userRole == null || userRole.equals("user") || userRole.equals("guest")) {
            httpStatus = 403;
        } else if (userRole.equals("admin")) {
            httpStatus = 200;
            Player player = new Player();
            player.setPlayerUUID(UUID.randomUUID().toString());
            setValues(
                    player,
                    name,
                    teamUUID

            );
            DataHandler.insertPLayer(player);
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates an existing player
     *
     * @param playerUUID the unique key of the player
     * @param name       the name of the player
     * @param teamUUID   the unique key of the team
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updatePlayer(
            @FormParam("playerUUID")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String playerUUID,

            @FormParam("name")
            @NotEmpty
            @Size(min = 2, max = 40)
                    String name,

            @FormParam("teamUUID")
            @NotEmpty
            @Pattern(regexp = "|[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String teamUUID,
            @CookieParam("userRole") String userRole

    ) {
        int httpStatus;
        if (userRole == null || userRole.equals("user") || userRole.equals("guest")) {
            httpStatus = 403;
        } else if (userRole.equals("admin")) {
            Player player;
            try {
                UUID.fromString(playerUUID);
                player = DataHandler.readPlayer(playerUUID);
                if (player.getName() != null) {
                    httpStatus = 200;
                    setValues(
                            player,
                            name,
                            teamUUID
                    );
                    DataHandler.updatePlayer();
                } else {
                    httpStatus = 404;
                }
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * deletes an existing player identified by its uuid
     *
     * @param playerUUID the unique key for the player
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deletePlayer(
            @FormParam("uuid")
            @NotEmpty
            @Pattern(regexp = "[0-9a-fA-F]{8}-([0-9a-fA-F]{4}-){3}[0-9a-fA-F]{12}")
                    String playerUUID,
            @CookieParam("userRole") String userRole
    ) {
        int httpStatus;

        if (userRole == null || userRole.equals("user") || userRole.equals("guest")) {
            httpStatus = 403;
        } else if (userRole.equals("admin")) {
            try {
                UUID.fromString(playerUUID);

                if (DataHandler.deletePlayer(playerUUID)) {
                    httpStatus = 200;

                } else {
                    httpStatus = 404;
                }
            } catch (IllegalArgumentException argEx) {
                httpStatus = 400;
            }
        } else {
            httpStatus = 404;
        }

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * sets the attribute values of the player object
     *
     * @param player   the player object
     * @param name     the player name
     * @param teamUUID the team of the player
     */
    private void setValues(
            Player player,
            String name,
            String teamUUID) {
        player.setName(name);
        Team team = DataHandler.getTeamMap().get(teamUUID);
        player.setTeam(team);

    }
}