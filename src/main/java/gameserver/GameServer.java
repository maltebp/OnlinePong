package gameserver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class GameServer {

    private ArrayList<Player> players = new ArrayList<Player>();
    private Sender sender;
    private GameController gameController;
    private MessageCreator messageCreator = new MessageCreator();
    private Matchmaker matchmaker;
    private Authenticator authenticator = new Authenticator();


    public GameServer(Sender sender){
        this.sender = sender;
        gameController = new GameController(sender, messageCreator);
        matchmaker = new Matchmaker(sender, gameController, messageCreator);
    }

    public void recieveMessage( Player player, String textMessage ){
        JSONObject msg = new JSONObject(textMessage);

        try {
            switch (msg.getInt("code")) {

                // Login
                case 1:
                    System.out.println("Authentication recieved");
                    String username = msg.getString("username");
                    String password = msg.getString("password");
                    if( authenticator.authenticatePlayer( username, password)){
                        if( !usernameExists(username)){
                            player.setUsername(username);
                            players.add(player);
                            matchmaker.addPlayer(player);
                        }else{
                            System.out.println("Already logged in");
                            sender.sendMessage(player, messageCreator.alreadyLoggedIn());
                        }
                    }else{
                        System.out.println("Wrong username password");
                        sender.sendMessage(player, messageCreator.wrongUsernamePassword());
                    }
                    break;

                // Accept game
                case 2:
                    if(checkAuthentication(player))
                        matchmaker.playerAcceptsMatch(player);
                    break;

                //
                case 10:
                    if( checkAuthentication(player))
                        gameController.dataRecieved(player, textMessage);
                    break;

                default:
                    sender.sendMessage(player, messageCreator.wrongMessageFormat("Unknown code"));
            }

        }catch(JSONException exception){
            // Wrong format
            sender.sendMessage(player, messageCreator.wrongMessageFormat());
        }
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

    private boolean checkAuthentication(Player player){
        if( players.contains(player)){
            return true;
        }else{
            sender.sendMessage(player, messageCreator.notAuthenticated());
            return false;
        }
    }


    public void removePlayer( Player player ){
        matchmaker.removePlayer(player);
        gameController.removePlayer(player);
        players.remove(player);
    }


}
