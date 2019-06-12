package gameserver.control;

import gameserver.model.Match;
import gameserver.model.Player;
import gameserver.view.Sender;

import java.util.HashMap;
import java.util.Random;

public class MatchController {

    private HashMap<Player, Match> playerGame = new HashMap<Player, Match>();
    private Sender sender;

    public MatchController(Sender sender){
        this.sender = sender;
    }


    public void startGame(Match match){
        playerGame.put(match.getPlayer(1), match);
        playerGame.put(match.getPlayer(2), match);

        Random rnd = new Random();
        boolean player1Starting = rnd.nextBoolean();

        sender.sendStartGame(match.getPlayer(1), player1Starting);
        sender.sendStartGame(match.getPlayer(1), !player1Starting);
    }


    public void dataRecieved( Player player, String dataMsg ){
        Match match = playerGame.get(player);
        Player opponent = match.getOpponent(player);
        if(opponent != null ){
            sender.sendMessage(opponent, dataMsg );
        }
    }


    private void stopGame(Match match){
        Player player = match.getPlayer(1);
        if(player != null){
            playerGame.remove(player);
        }
        player = match.getPlayer(2);
        if( player != null){
            playerGame.remove(player);
        }
    }


    public void removePlayer(Player player){
        Match match = playerGame.get(player);

        if( match != null ){
            Player opponent = match.getOpponent(player);
            sender.sendOpponentDisconnected(opponent);
            stopGame(match);
        }

    }
}
