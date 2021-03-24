package football.model;

/**
 * a football Team
 * <p>
 * Football
 *
 * @author David Feer
 */
public class Trainer {
    private String trainerUUID;
    private String name;
    private Team team;


    /**
     * Gets the trainerUUID
     *
     * @return value of trainerUUID
     */
    public String getTrainerUUID() {
        return trainerUUID;
    }

    /**
     * Sets the trainerUUID
     *
     * @param trainerUUID the value to set
     */

    public void setTrainerUUID(String trainerUUID) {
        this.trainerUUID = trainerUUID;
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