package gameserver.control;

import gameserver.model.Player;

public class DatabaseConnector {
Decoder decodeMessages = new Decoder();

    /**
     * Retrieve information about the user from the databse
     * and updates the given player object with the information
     *
     * @param player Player object to put the data into
     */
    public void setPlayerInformation(Player player) {
        //TODO: Implement this
    }


    /**
     * Authenticates a given username + password combination
     */
    public boolean authenticatePlayer(String username, String password){
        //TODO: Implement this
        return true;
    }


    /**
     * Updates the Elo-rating of the player in the database
     * to the current rating of the player object.
     * @param player Player object to retrieve Elo-rating from
     */
    public void updateElo(Player player){
        //TODO: Implement this
    }
}


