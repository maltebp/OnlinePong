package gameserver;

import javax.websocket.Session;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;

public class PlayerController {

    private HashMap<String, Player> players = new HashMap<String, Player>();
    private LinkedList<Player> lookingForGame = new LinkedList<Player>();


    public void removePlayer(){

    }



    public LinkedList<Player> getPlayersLookingForGame(){
        return lookingForGame;
    }

    public void setPlayersInGame(Player ... players){
        for( Player player : players) lookingForGame.remove(player);
    }

    public Player addPlayer(Session session, String username){
        if( true || ! playerExists(username)) {
            Player player = new Player(session, username);
            players.put(session.getId(), player);
            lookingForGame.add(player);
            return player;
        }
        return null;
    }


    public Player getPlayer(Session session){
        return players.get(session.getId());
    }


    private boolean playerExists( String username ){

        Iterator it = players.entrySet().iterator();

        while (it.hasNext()) {
            Player player = (Player) ((Map.Entry) it.next()).getValue();

            if( player.getName().equals(username) ) return true;

        }
        return false;
    }

}
