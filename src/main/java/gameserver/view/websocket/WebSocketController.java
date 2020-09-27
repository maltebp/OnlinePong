package gameserver.view.websocket;


import gameserver.model.Player;
import org.eclipse.jetty.websocket.api.*;
import gameserver.view.Sender;

import gameserver.control.GameServer;

import java.util.HashMap;


public class WebSocketController extends Sender {

    private GameServer gameServer = new GameServer(this);
    private HashMap<Session, Player> players = new HashMap<Session, Player>();
    private HashMap<Player, Session> sessions = new HashMap<Player, Session>();

    public void openSession(Session session){
        Player player = new Player();
        players.put(session, player );
        sessions.put(player, session);
    }

    public void messageRecieved(Session session, String message){
        Player player = players.get(session);
        if( player != null ){
            gameServer.recieveMessage(player, message);
        }
    }

    public void closeSession(Session session ){
        Player player = players.get(session);
        if( player != null ){
            gameServer.removePlayer(player);
            players.remove(session);
            sessions.remove(player);
        }
    }

    @Override
    public void sendMessage(Player player, String message ){
        Session session = sessions.get(player);
        try {
            if( session.isOpen() ){
                session.getRemote().sendString(message);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
