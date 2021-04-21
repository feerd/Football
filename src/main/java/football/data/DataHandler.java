package football.data;

import football.model.Player;
import football.model.Team;
import football.model.Trainer;
import football.model.User;
import football.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * data handler for reading and writing the csv files
 * <p>
 * M133: Football
 *
 * @author David Feer
 */

public class DataHandler {
    private static final DataHandler instance = new DataHandler();
    private static Map<String, Player> playerMap;
    private static Map<String, Team> teamMap;
    private static Map<String, User> userMap;
    private static Map<String, Trainer> trainerMap;

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        playerMap = new HashMap<>();
        teamMap = new HashMap<>();
        userMap = new HashMap<>();
        trainerMap = new HashMap<>();
        readJSON();
    }

    /**
     * reads a single player identified by its uuid
     *
     * @param playerUUID the identifier
     * @return player-object
     */
    public static Player readPlayer(String playerUUID) {
        Player player = new Player();
        if (getPlayerMap().containsKey(playerUUID)) {
            player = getPlayerMap().get(playerUUID);
        }
        return player;
    }

    /**
     * saves a player
     *
     * @param player the player to be saved
     */
    public static void savePlayer(Player player) {
        getPlayerMap().put(player.getPlayerUUID(), player);
        writeJSON();
    }

    /**
     * reads a single team identified by its uuid
     *
     * @param teamUUID the identifier
     * @return team-object
     */
    public static Team readTeam(String teamUUID) {
        Team team = new Team();
        if (getTeamMap().containsKey(teamUUID)) {
            team = getTeamMap().get(teamUUID);
        }
        return team;
    }

    /**
     * reads a single team identified by its uuid
     *
     * @param userUUID the identifier
     * @return user-object
     */
    public static User readUser(String userUUID) {
        User user = new User();
        if (getTeamMap().containsKey(userUUID)) {
            user = getUserMap().get(userUUID);
        }
        return user;
    }

    /**
     * reads a single trainer identified by its uuid
     *
     * @param userUUID the identifier
     * @return trainer-object
     */
    public static Trainer readTrainer(String userUUID) {
        Trainer trainer = new Trainer();
        if (getTeamMap().containsKey(userUUID)) {
            trainer = getTrainerMap().get(userUUID);
        }
        return trainer;
    }


    /**
     * saves a trainer
     *
     * @param team the trainer to be saved
     */
    public static void saveTeam(Team team) {
        getTeamMap().put(team.getTeamUUID(), team);
        writeJSON();
    }

    /**
     * gets the playerMap
     *
     * @return the playerMap
     */
    public static Map<String, Player> getPlayerMap() {
        return playerMap;
    }

    /**
     * gets the teamMap
     *
     * @return the teamMap
     */
    public static Map<String, Team> getTeamMap() {
        return teamMap;
    }

    /**
     * gets the userMap
     *
     * @return the userMap
     */
    public static Map<String, User> getUserMap() {
        return userMap;
    }

    /**
     * gets the userMap
     *
     * @return the userMap
     */
    public static Map<String, Trainer> getTrainerMap() {
        return trainerMap;
    }

    /**
     * sets the teamMap
     *
     * @param teamMap the value to set
     */
    public static void setTeamMap(Map<String, Team> teamMap) {
        DataHandler.teamMap = teamMap;
    }

    /**
     * inserts a new player into the playermap
     *
     * @param player the player to be saved
     */
    public static void insertPLayer(Player player) {
        getPlayerMap().put(player.getPlayerUUID(), player);
        writeJSON();
    }

    /**
     * updates the playerMap
     *
     */
    public static void updatePlayer() {
        writeJSON();
    }

    /**
     * removes a player from the playermap
     *
     * @param playerUUID the uuid of the player to be removed
     * @return success
     */
    public static boolean deletePlayer(String playerUUID) {
        if (getPlayerMap().remove(playerUUID) != null) {
            writeJSON();
            return true;
        } else
            return false;
    }

    /**
     * inserts a new team into the teamMap
     *
     * @param team the player to be saved
     */
    public static void insertTeam(Team team) {
        Player player = new Player();
        player.setPlayerUUID(UUID.randomUUID().toString());
        player.setName("");
        player.setTeam(team);
        insertPLayer(player);
    }

    /**
     * updates the teamMap
     *
     * @param team to save
     */
    public static boolean updateTeam(Team team) {
        boolean found = false;
        for (Map.Entry<String, Player> entry : getPlayerMap().entrySet()) {
            Player player = entry.getValue();
            if (player.getTeam().getTeamUUID().equals(team.getTeamUUID())) {
                player.setTeam(team);
                found = true;
            }
        }
        writeJSON();
        return found;
    }

    /**
     * removes a team from the teamMap
     *
     * @param teamUUID the uuid of the team to be removed
     * @return success
     */
    public static int deleteTeam(String teamUUID) {
        int errorcode = 1;
        for (Map.Entry<String, Player> entry : getPlayerMap().entrySet()) {
            Player player = entry.getValue();
            if (player.getTeam().getTeamUUID().equals(teamUUID)) {
                if (player.getName() == null || player.getName().equals("")) {
                    deletePlayer(player.getPlayerUUID());
                    errorcode = 0;
                } else {
                    return -1;
                }
            }
        }

        writeJSON();
        return errorcode;
    }

    /**
     * reads the players and teams
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("playerJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Player[] players = objectMapper.readValue(jsonData, Player[].class);
            for (Player player : players) {
                String teamUUID = player.getTeam().getTeamUUID();
                Team team;
                if (getTeamMap().containsKey(teamUUID)) {
                    team = getTeamMap().get(teamUUID);
                } else {
                    team = player.getTeam();
                    getTeamMap().put(teamUUID, team);
                }
                player.setTeam(team);
                getPlayerMap().put(player.getPlayerUUID(), player);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the players and teams
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String playerPath = Config.getProperty("playerJSON");
        try {
            fileOutputStream = new FileOutputStream(playerPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getPlayerMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
