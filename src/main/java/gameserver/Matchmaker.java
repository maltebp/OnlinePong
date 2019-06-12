package gameserver;

import java.util.LinkedList;

public class Matchmaker {

    public Player findMatch( Player player, LinkedList<Player> possibleOpponents){

        if( possibleOpponents.size() > 1 ) {
            for (Player opponent : possibleOpponents) {
                if (opponent != player) {
                    return opponent;
                }
            }
        }
        return null;
    }


}
