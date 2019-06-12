package gameserver;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.HashMap;

@ServerEndpoint("/gameserver")
public class Connector implements Sender {

    private GameServer gameServer = new GameServer(this);
    private HashMap<Session, Player> players = new HashMap<Session, Player>();
    private HashMap<Player, Session> sessions = new HashMap<Player, Session>();


    @OnOpen
    public void onOpen(Session session){
        players.put(session, new Player());
    }

    @OnMessage
    public void onMessage( Session session, String msg ) throws Exception{
        Player player = players.get(session);
        if( player != null ){
            gameServer.recieveMessage(player, msg);
        }
    }

    @OnClose
    public void onClose( Session session ) throws Exception{
        Player player = players.get(session);
        if( player != null ){
            gameServer.removePlayer(player);
            players.remove(session);
            sessions.remove(player);
        }
    }

    @OnError
    public void onError(Throwable t){
        System.out.println("Some error occured: "+t.getMessage());
    }


    public void sendMessage(Player player, String message ){
        Session session = sessions.get(player);
        try {
            if( !session.isOpen() ){
                session.getBasicRemote().sendText(message);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
