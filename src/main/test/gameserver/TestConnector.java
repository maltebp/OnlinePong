package gameserver;

import gameserver.control.GameServer;
import gameserver.model.Player;
import gameserver.view.Sender;
import org.json.JSONObject;

import static java.lang.Thread.sleep;

public class TestConnector extends Sender {

    private GameServer server = new GameServer(this);

    void recieveMessage(Player player, String message){
        server.recieveMessage(player, message);
    }

    @Override
    public void sendMessage(Player player, String message) {
        TestPlayer testPlayer = (TestPlayer) player;
        JSONObject json = new JSONObject(message);
        switch(json.getInt("code")){
            case 102:
                testPlayer.isMatched = true;
        }
        System.out.println(player.getUsername()+": "+message);
    }

}
