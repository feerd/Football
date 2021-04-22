package football.service;


import football.data.DataHandler;
import football.model.Team;
import football.model.Year;

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
    public Response listTeams() {
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
    public Response readTeam(@QueryParam("uuid") String teamUUID) {
        Team team = null;
        int httpStatus;

        try {
            UUID.fromString(teamUUID);
            team = DataHandler.readTeam(teamUUID);
            if (team.getName() == null) {
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argumentException) {
            httpStatus = 400;
        }


        Response response = Response
                .status(httpStatus)
                .entity(team)
                .build();

        return response;
    }

    /**
     * creates a new team without players
     *
     * @param teamName
     * @return Response
     */
    @POST
    @Path("create")
    @Produces(MediaType.TEXT_PLAIN)
    public Response createTeam(
            @FormParam("teamName")
                    String teamName,

            @FormParam("foundingYear")
            @Year(2020)
                    int foundingYear,

            @FormParam("shortcut")
                    String shortcut

    ) {
        int httpStatus = 200;
        Team team = new Team();

        team.setTeamUUID(UUID.randomUUID().toString());
        setValues(team, foundingYear, shortcut, teamName);
        DataHandler.insertTeam(team);

        Response response = Response
                .status(httpStatus)
                .entity("")
                .build();
        return response;
    }

    /**
     * updates the team in all it's players
     *
     * @param teamUUID the uuid of the team
     * @param teamName the new teamName
     * @return Response
     */
    @PUT
    @Path("update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response updateTeam(
            @FormParam("teamUUID") String teamUUID,
            @FormParam("teamName") String teamName
    ) {
        int httpStatus = 200;
        Team team = new Team();
        try {
            UUID.fromString(teamUUID);
            team.setTeamUUID(teamUUID);
            team.setName(teamName);
            if (DataHandler.updateTeam(team)) {
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
     * deletes an existing team identified by its uuid
     *
     * @param teamUUID the unique key for the team
     * @return Response
     */
    @DELETE
    @Path("delete")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteTeam(
            @QueryParam("teamUUID") String teamUUID
    ) {
        int httpStatus;
        try {
            UUID.fromString(teamUUID);
            int errorcode = DataHandler.deleteTeam(teamUUID);
            if (errorcode == 0) httpStatus = 200;
            else if (errorcode == -1) httpStatus = 409;
            else httpStatus = 404;
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
     * sets the attribute values of the team object
     *
     * @param team         the team
     * @param foundingYear of the team
     * @param shortcut     of the team
     * @param name         of the team
     */
    private void setValues(
            Team team,
            int foundingYear,
            String shortcut,
            String name) {
        team.setName(name);
        team.setFoundingYear(foundingYear);
        team.setShortcut(shortcut);
    }
}

