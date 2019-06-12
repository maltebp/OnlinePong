package gameserver.control;

import gameserver.model.Player;
import gameserver.view.Sender;
import org.json.JSONException;
import org.json.JSONObject;


public class GameServer {

    private Sender sender;
    private MatchController matchController;
    private Matchmaker matchmaker;
    private PlayerController playerController;


    public GameServer(Sender sender){
        this.sender = sender;
        playerController = new PlayerController(sender);
        matchController = new MatchController(sender);
        matchmaker = new Matchmaker(sender, matchController);
    }

    public void recieveMessage(Player player, String textMessage ){
        JSONObject msg = new JSONObject(textMessage);

        try {
            switch (msg.getInt("code")) {

                // Login
                case 1:
                    String username = msg.getString("username");
                    String password = msg.getString("password");
                    if( playerController.addPlayer(player, username, password) ){
                        matchmaker.addPlayer(player);
                    }
                    break;

                // Accept game
                case 2:
                    if(playerController.playerIsAuthenticated(player))
                        matchmaker.playerAcceptsMatch(player);
                    break;

                //
                case 10:
                    if( playerController.playerIsAuthenticated(player))
                        matchController.dataRecieved(player, textMessage);
                    break;

                default:
                    sender.sendWrongMessageFormat(player, "Unknown code");
            }

        }catch(JSONException exception){
            // Wrong format
            sender.sendWrongMessageFormat(player);
        }
    }


    public void removePlayer( Player player ){
        matchmaker.removePlayer(player);
        matchController.removePlayer(player);
        playerController.removePlayer(player);
    }

}
