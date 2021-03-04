package football.model;

/**
 * a team of a player
 * <p>
 * Football
 *
 * @author David Feer
 */
public class Team {
    private String teamUUID;
    private String publisher;

    /**
     * Gets the publisherUUID
     *
     * @return value of publisherUUID
     */
    public String getTeamUUID() {
        return teamUUID;
    }

    /**
     * Sets the publisherUUID
     *
     * @param teamUUID the value to set
     */

    public void setTeamUUID(String teamUUID) {
        this.teamUUID = teamUUID;
    }

    /**
     * Gets the publisher
     *
     * @return value of publisher
     */
    public String getPublisher() {
        return publisher;
    }

    /**
     * Sets the publisher
     *
     * @param publisher the value to set
     */

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}