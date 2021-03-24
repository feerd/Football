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
     * Gets the playerUUID
     *
     * @return value of playerUUID
     */
    public String getPlayerUUID() {
        return playerUUID;
    }

    /**
     * Sets the playerUUID
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
     * Gets the team
     *
     * @return value of team
     */
    public Team getTeam() {
        return team;
    }

    /**
     * Sets the team
     *
     * @param team the value to set
     */

    public void setTeam(Team team) {
        this.team = team;
    }


}