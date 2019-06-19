package gameserver.control;

import gameserver.control.databaseconnector.APIConnector;
import gameserver.control.databaseconnector.DatabaseConnector;
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
    private MatchController matchController  = new MatchController(this);
    private Matchmaker matchmaker = new Matchmaker(this);
    private AuthenticationController authenticationController = new AuthenticationController(this);
    private DatabaseConnector databaseConnector = new APIConnector();


    public GameServer(Sender sender){
        this.sender = sender;
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
                    if( authenticationController.authenticatePlayer(player, username, password, databaseConnector) ){
                        matchmaker.addPlayer(player);
                    }
                    break;

                // Accept game
                case 2:
                    if(authenticationController.playerIsAuthenticated(player))
                        matchmaker.playerAcceptsMatch(player);
                    break;

                // Game Data
                case 10:
                    if( authenticationController.playerIsAuthenticated(player))
                        matchController.dataRecieved(player, textMessage);
                    break;

                case 11:
                    if( authenticationController.playerIsAuthenticated(player)) {
                        Player winner = matchController.matchFinished(player, false);
                        authenticationController.removePlayer(player);
                        authenticationController.removePlayer(winner);
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
        authenticationController.removePlayer(player);
    }


    public Sender getSender() {
        return sender;
    }


    public MatchController getMatchController() {
        return matchController;
    }

    public DatabaseConnector getDatabaseConnector(){
        return databaseConnector;
    }

    public void setDatabaseConnector(DatabaseConnector databaseConnector){
        this.databaseConnector = databaseConnector;

    }


}
