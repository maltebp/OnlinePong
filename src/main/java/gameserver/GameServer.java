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

    public void recieveMessage( Player player, String textMessage ) throws GameServerException{
        JSONObject msg = new JSONObject(textMessage);

        try {
            switch (msg.getInt("code")) {

                // Login
                case 1:
                    if( authenticator.authenticatePlayer(msg.getString("username"), msg.getString("password"))){
                        players.add(player);
                        sender.sendMessage(player, messageCreator.findingGame(0));
                        matchmaker.addPlayer(player);
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

    private boolean checkAuthentication(Player player){
        if( players.contains(player)){
            return true;
        }else{
            sender.sendMessage(player, messageCreator.notAuthenticated());
            return false;
        }
    }


    public void removePlayer( Player player ) throws GameServerException{
        matchmaker.removePlayer(player);
        gameController.removePlayer(player);
        players.remove(player);
    }


}
