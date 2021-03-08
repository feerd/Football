package football.data;

import football.model.Player;
import football.model.Team;
import football.model.User;
import football.service.Config;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

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

    /**
     * default constructor: defeat instantiation
     */
    private DataHandler() {
        playerMap = new HashMap<>();
        teamMap = new HashMap<>();
        userMap = new HashMap<>();

        readJSON();
    }

    /**
     * reads a single book identified by its uuid
     *
     * @param bookUUID the identifier
     * @return book-object
     */
    public static Player readPlayer(String bookUUID) {
        Player player = new Player();
        if (getPlayerMap().containsKey(bookUUID)) {
            player = getPlayerMap().get(bookUUID);
        }
        return player;
    }

    /**
     * saves a book
     *
     * @param player the book to be saved
     */
    public static void savePlayer(Player player) {
        getPlayerMap().put(player.getPlayerUUID(), player);
        writeJSON();
    }

    /**
     * reads a single publisher identified by its uuid
     *
     * @param publisherUUID the identifier
     * @return publisher-object
     */
    public static Team readTeam(String publisherUUID) {
        Team team = new Team();
        if (getTeamMap().containsKey(publisherUUID)) {
            team = getTeamMap().get(publisherUUID);
        }
        return team;
    }

    /**
     * reads a single publisher identified by its uuid
     *
     * @param userUUID the identifier
     * @return publisher-object
     */
    public static User readUser(String userUUID) {
        User user = new User();
        if (getTeamMap().containsKey(userUUID)) {
            user = getUserMap().get(userUUID);
        }
        return user;
    }


    /**
     * saves a publisher
     *
     * @param team the publisher to be saved
     */
    public static void saveTeam(Team team) {
        getTeamMap().put(team.getTeamUUID(), team);
        writeJSON();
    }

    /**
     * gets the bookMap
     *
     * @return the bookMap
     */
    public static Map<String, Player> getPlayerMap() {
        return playerMap;
    }

    /**
     * gets the publisherMap
     *
     * @return the publisherMap
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
     * sets the teamMap
     *
     * @param teamMap the value to set
     */
    public static void setTeamMap(Map<String, Team> teamMap) {
        DataHandler.teamMap = teamMap;
    }

    /**
     * reads the books and publishers
     */
    private static void readJSON() {
        try {
            byte[] jsonData = Files.readAllBytes(Paths.get(Config.getProperty("bookJSON")));
            ObjectMapper objectMapper = new ObjectMapper();
            Player[] players = objectMapper.readValue(jsonData, Player[].class);
            for (Player book : players) {
                String publisherUUID = book.getPublisher().getTeamUUID();
                Team team;
                if (getTeamMap().containsKey(publisherUUID)) {
                    team = getTeamMap().get(publisherUUID);
                } else {
                    team = book.getPublisher();
                    getTeamMap().put(publisherUUID, team);
                }
                book.setPublisher(team);
                getPlayerMap().put(book.getPlayerUUID(), book);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * write the books and publishers
     */
    private static void writeJSON() {
        ObjectMapper objectMapper = new ObjectMapper();
        Writer writer;
        FileOutputStream fileOutputStream = null;

        String bookPath = Config.getProperty("playerJSON");
        try {
            fileOutputStream = new FileOutputStream(bookPath);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream, StandardCharsets.UTF_8));
            objectMapper.writeValue(writer, getPlayerMap().values());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
