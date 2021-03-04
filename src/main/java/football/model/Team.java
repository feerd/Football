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
    private String name;
    private String shortcut;
    private int foundingYear;

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
     * @return name
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortcut() {
        return shortcut;
    }

    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    public int getFoundingYear() {
        return foundingYear;
    }

    public void setFoundingYear(int foundingYear) {
        this.foundingYear = foundingYear;
    }
}