package football.service;


import football.data.DataHandler;
import football.model.Player;
import football.model.Team;

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
     * reads a single player identified by the playerId
     *
     * @param playerUUID identifier
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readBook(@QueryParam("uuid") String playerUUID) {
        Player player = null;
        int httpStatus;

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


        Response response = Response
                .status(httpStatus)
                .entity(player)
                .build();

        return response;
    }

    /**
     * creates a new book
     *
     * @param name     the name of the player
     * @param teamUUID the unique key of the team
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createBook(
            @FormParam("name") String name,
            @FormParam("teamUUID") String teamUUID
    ) {
        int httpStatus = 200;
        Player player = new Player();
        player.setPlayerUUID(UUID.randomUUID().toString());
        setValues(
                player,
                name,
                teamUUID

        );

        DataHandler.insertPLayer(player);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates an existing book
     *
     * @param playerUUID the unique key of the player
     * @param name       the name of the player
     * @param teamUUID   the unique key of the team
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateBook(
            @FormParam("playerUUID") String playerUUID,
            @FormParam("author") String name,
            @FormParam("teamUUID") String teamUUID
    ) {
        int httpStatus = 200;
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

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * deletes an existing book identified by its uuid
     *
     * @param playerUUID the unique key for the book
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteBook(
            @QueryParam("uuid") String playerUUID
    ) {
        int httpStatus;
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

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * sets the attribute values of the book object
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