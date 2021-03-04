package football.model;

/**
 * a football player
 * <p>
 * Football
 *
 * @author David Feer
 */
public class Player {
    private String playerUUID;
    private String name;
    private Team team;


    /**
     * Gets the bookUUID
     *
     * @return value of bookUUID
     */
    public String getPlayerUUID() {
        return playerUUID;
    }

    /**
     * Sets the bookUUID
     *
     * @param playerUUID the value to set
     */

    public void setPlayerUUID(String playerUUID) {
        this.playerUUID = playerUUID;
    }

    /**
     * Gets the title
     *
     * @return value of title
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the title
     *
     * @param name the value to set
     */

    public void setName(String name) {
        this.name = name;
    }



    /**
     * Gets the publisher
     *
     * @return value of publisher
     */
    public Team getPublisher() {
        return team;
    }

    /**
     * Sets the publisher
     *
     * @param team the value to set
     */

    public void setPublisher(Team team) {
        this.team = team;
    }


}