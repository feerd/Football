package football.model;

/**
 * user of the online service
 * <p>
 * Football
 *
 * @author David Feer
 */
public class User {
    private String userUUID;
    private String userName;
    private String password;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * logs the user on
     */
    public void logon() {

    }


    /**
     * logs the user off
     */
    public void logoff() {

    }
}
