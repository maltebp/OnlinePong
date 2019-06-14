package gameserver.control;

import gameserver.model.Player;
import gameserver.view.Sender;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * The central controller of the Game Server
 *
 * It analyses incoming messages and initiaties required
 * processes from helping controllers:
 *
 * It isolates Game Server functionality from connection
 * functionality, creating an abstract "connectection"
 * between Game Server and Game client.
 */
public class GameServer {

    private Sender sender;
    private MatchController matchController;
    private Matchmaker matchmaker;
    private PlayerController playerController;
    private DatabaseConnector databaseConnector = new DatabaseConnector();


    public GameServer(Sender sender){
        this.sender = sender;
        playerController = new PlayerController(sender);
        matchController = new MatchController(sender);
        matchmaker = new Matchmaker(sender, matchController);
    }


    /**
     * Analyze a given message in the format defined in the
     * Game Server Message System, and starts required
     * processes in helping controllers.
     */
    public void recieveMessage(Player player, String textMessage ){
        JSONObject msg = new JSONObject(textMessage);

        try {
            switch (msg.getInt("code")) {

                // Find game / Login
                case 1:
                    String username = msg.getString("username");
                    String password = msg.getString("password");
                    if( playerController.addPlayer(player, username, password, databaseConnector) ){
                        matchmaker.addPlayer(player);
                    }
                    break;

                // Accept game
                case 2:
                    if(playerController.playerIsAuthenticated(player))
                        matchmaker.playerAcceptsMatch(player);
                    break;

                // Game Data
                case 10:
                    if( playerController.playerIsAuthenticated(player))
                        matchController.dataRecieved(player, textMessage);
                    break;

                case 11:
                    if( playerController.playerIsAuthenticated(player)) {
                        matchController.matchFinished(player, false);
                        playerController.removePlayer(player);
                    }
                    break;

                // Code not recognized
                default:
                    sender.sendWrongMessageFormat(player, "Unknown code");
            }

        // Wrong format (syntax/missing variable)
        }catch(JSONException exception){
            sender.sendWrongMessageFormat(player);
        }
    }


    /**
     * Disconnects a Player from the game server, removing him
     * from games, matchmaking, authentications.
     */
    public void removePlayer( Player player ){
        matchmaker.removePlayer(player);
        matchController.removePlayer(player);
        playerController.removePlayer(player);
    }

}
