import gameserver.model.Player;
import gameserver.view.Sender;

import java.util.HashMap;

public class TestConnector extends Sender {

    HashMap<Player, TestPlayer> players = new HashMap<>();

    @Override
    public void sendMessage(Player player, String message) {
        TestPlayer testPlayer = players.get(player);
        System.out.println();
    }
}
