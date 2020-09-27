package gameserver.control.databaseconnector;

import gameserver.model.Player;

import javax.xml.crypto.Data;


/**
 * Makes contacts to the database used by
 * the game server. The interface isolates
 * the connection method to the database
 * and the database itself.
 */
public interface DatabaseConnector {


    /**
     * Retrieve information about the user from the databse
     * and updates the given player object with the information
     *
     * @param player Player object to put the data into
     */
    void getPlayerInformation(Player player) throws DatabaseException;


    /**
     * Authenticates a given username + password combination
     */
    boolean authenticatePlayer(String username, String password) throws DatabaseException;


    /**
     * Updates the Elo-rating of the player in the database
     * to the current rating of the player object.
     * @param player Player object to retrieve Elo-rating from
     */
    void updateElo(Player player) throws DatabaseException;



    class DatabaseException extends Exception {
        public DatabaseException(String msg){ super(msg); }
    }
}
