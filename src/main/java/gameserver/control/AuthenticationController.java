package gameserver.control;

import gameserver.control.databaseconnector.DatabaseConnector;
import gameserver.model.Player;

import java.util.ArrayList;


/**
 * Controller which handles authenticatedPlayers connected to the
 * game server.
 * It validates users via authenticating (username/password)
 * and duplicate Player checks (usernameExists()), and maintains
 * information about validated users.
 *
 */
class AuthenticationController {

    private GameServer server;

    // Validated users
    private ArrayList<Player> authenticatedPlayers = new ArrayList<>();


    AuthenticationController(GameServer server){
        this.server = server;
    }


    /**
     * Validate the player and adds it to the list of validated
     * Players (authenticatedPlayers connected to the Game server.Server).
     *
     * @return Whether or not the player was validated
     */
    boolean authenticatePlayer(Player player, String username, String password, DatabaseConnector databaseConnector ){

        if( databaseConnector.authenticatePlayer(username, password) ){

            if( !usernameExists(username)){
                authenticatedPlayers.add(player);
                player.setUsername(username);
                databaseConnector.getPlayerInformation(player);
                return true;
            }else{
                System.out.println("Already logged in");
                server.getSender().sendAlreadyLoggedIn(player);
            }
        }else{
            System.out.println("Wrong username password");
            server.getSender().sendWrongUsernamePassword(player);
        }
        return false;
    }

    /**
     * Removes the player as a validated user
     */
    void removePlayer(Player player){
        authenticatedPlayers.remove(player);
    }


    /**
     * @return True if the player has been authenticated (via authenticatePlayer())
     */
    boolean playerIsAuthenticated(Player player){
        if( authenticatedPlayers.contains(player)){
            return true;
        }else{
            server.getSender().sendNotAuthenticated(player);
            return false;
        }
    }

    /**
     * Checks if a Player with a given username is already connected
     * to the game server.
     */
    private boolean usernameExists(String username){
        for( Player player : authenticatedPlayers){
            if( player.getUsername().equals(username) ){
                return true;
            }
        }
        return false;
    }
}
