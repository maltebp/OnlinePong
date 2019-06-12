package gameserver.control;

import gameserver.model.Player;
import gameserver.view.Sender;
import org.json.JSONException;
import org.json.JSONObject;


public class GameServer {

    private Sender sender;
    private GameController gameController;
    private MessageCreator messageCreator = new MessageCreator();
    private Matchmaker matchmaker;
    private PlayerController playerController;


    public GameServer(Sender sender){
        this.sender = sender;
        playerController = new PlayerController(sender, messageCreator);
        gameController = new GameController(sender, messageCreator);
        matchmaker = new Matchmaker(sender, gameController, messageCreator);
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


    public void removePlayer( Player player ){
        matchmaker.removePlayer(player);
        gameController.removePlayer(player);
        playerController.removePlayer(player);
    }

}
