package gameserver.view;

import gameserver.model.Player;

public interface Sender {
    void sendMessage(Player player, String message);
}
