package gameserver.control;

import gameserver.model.Player;
import gameserver.view.Sender;
import java.util.ArrayList;


public class PlayerController {

    private Sender sender;

    private ArrayList<Player> players = new ArrayList<Player>();

    public PlayerController(Sender sender){
        this.sender = sender;
    }

    /**
     * Checks if the player is valid by authenticating it
     * and checking its already logged in.
     */
    public boolean addPlayer( Player player, String username, String password ){
        if( authenticatePlayer(username, password) ){

            if( !usernameExists(username)){
                players.add(player);
                return false;
            }else{
                System.out.println("Already logged in");
                sender.sendAlreadyLoggedIn(player);
            }
        }else{
            System.out.println("Wrong username password");
            sender.sendWrongUsernamePassword(player);
        }
        return false;
    }

    public void removePlayer(Player player){
        players.remove(player);
    }

    public boolean playerIsAuthenticated(Player player){
        if( players.contains(player)){
            return true;
        }else{
            sender.sendNotAuthenticated(player);
            return false;
        }
    }


    private boolean authenticatePlayer(String username, String password){
        return true;
    }

    private boolean usernameExists(String username){
        return false;
        // TODO: Turn this on
        /*for( Player player : players ){
            if( player.getUsername().equals(username) ){
                return true;
            }
        }
        return false;*/
    }
}
