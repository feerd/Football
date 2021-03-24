package football.service;


import football.data.DataHandler;
import football.model.Player;
import football.model.Trainer;

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
@Path("trainer")
public class TrainerService {

    /**
     * produces a map of all trainers
     *
     * @return Response
     */
    @GET
    @Path("list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTrainers() {
        Map<String, Trainer> trainerMap = DataHandler.getTrainerMap();

        Response response = Response
                .status(200)
                .entity(trainerMap)
                .build();

        return response;
    }

    /**
     * reads a single player identified by the playerId
     *
     * @param trainerUUID identifier
     * @return Response
     */
    @GET
    @Path("read")
    @Produces(MediaType.APPLICATION_JSON)
    public Response readTrainer(@QueryParam("uuid") String trainerUUID) {
        Trainer trainer = null;
        int httpStatus;

        try {
            UUID.fromString(trainerUUID);
            trainer = DataHandler.readTrainer(trainerUUID);
            if (trainer.getName() == null) {
                httpStatus = 404;
            } else {
                httpStatus = 200;
            }
        } catch (IllegalArgumentException argumentException) {
            httpStatus = 400;
        }


        Response response = Response
                .status(httpStatus)
                .entity(trainer)
                .build();

        return response;
    }


}
