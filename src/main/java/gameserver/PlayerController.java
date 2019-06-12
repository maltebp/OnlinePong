package gameserver;

import java.util.ArrayList;

public class PlayerController {

    private Sender sender;
    private MessageCreator messageCreator;

    private ArrayList<Player> players = new ArrayList<Player>();

    public PlayerController(Sender sender, MessageCreator messageCreator){
        this.sender = sender;
        this.messageCreator = messageCreator;
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
                sender.sendMessage(player, messageCreator.alreadyLoggedIn());
            }
        }else{
            System.out.println("Wrong username password");
            sender.sendMessage(player, messageCreator.wrongUsernamePassword());
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
            sender.sendMessage(player, messageCreator.notAuthenticated());
            return false;
        }
    }


    private boolean authenticatePlayer(String username, String password){
        return true;
    }

    private boolean usernameExists(String username){
        return false;
        /*for( Player player : players ){
            if( player.getUsername().equals(username) ){
                return true;
            }
        }
        return false;*/
    }
}
