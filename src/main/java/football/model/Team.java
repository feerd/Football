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
     * Gets the teamUUID
     *
     * @return value of teamUUID
     */
    public String getTeamUUID() {
        return teamUUID;
    }

    /**
     * Sets the teamUUID
     *
     * @param teamUUID the value to set
     */

    public void setTeamUUID(String teamUUID) {
        this.teamUUID = teamUUID;
    }

    /**
     * gets the name
     *
     * @return value of name
     */
    public String getName() {
        return name;
    }

    /**
     * sets the name
     *
     * @param name the value to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gets the shortcut
     *
     * @return value of shortcut
     */
    public String getShortcut() {
        return shortcut;
    }


    /**
     * sets the shortcut
     *
     * @param shortcut the value to set
     */
    public void setShortcut(String shortcut) {
        this.shortcut = shortcut;
    }

    /**
     * gets the foundingYear
     *
     * @return value of foundingYear
     */
    public int getFoundingYear() {
        return foundingYear;
    }


    /**
     * sets the foundingYear
     *
     * @param foundingYear the value to set
     */
    public void setFoundingYear(int foundingYear) {
        this.foundingYear = foundingYear;
    }
}