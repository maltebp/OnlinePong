package gameserver.testobjects;

import gameserver.control.GameServer;
import gameserver.model.Player;
import gameserver.view.Sender;
import java.util.HashMap;


/**
 * Implementing Web Socket end point functionality
 * for testing purposes, as well as Sender
 * functionality (just like Web Socket Endpoint)
 */
public class ServerConnector extends Sender {


    private GameServer server = new GameServer(this);
    private HashMap<Player, ClientConnector> sessions = new HashMap<>();
    private HashMap<ClientConnector, Player> players = new HashMap<>();

    void createConnection(ClientConnector session, int rating){
        Player player = new Player();
        player.setRating(rating);
        sessions.put(player, session);
        players.put(session, player);
    }

    void closeConnection(ClientConnector session){
        server.removePlayer(players.get(session));
    }

    void recieveMessage(ClientConnector session, String message){
        System.out.println("Server: "+message);
        server.recieveMessage(players.get(session), message);
    }

    @Override
    public void sendMessage(Player player, String message) {
        sessions.get(player).recieveMessage(message);
    }

}
