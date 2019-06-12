package gameserver;

import java.util.HashMap;
import java.util.Random;

public class GameController {

    private HashMap<Player, Game> playerGame = new HashMap<Player, Game>();
    private Sender sender;
    private MessageCreator messageCreator;

    public GameController(Sender sender, MessageCreator messageCreator){
        this.sender = sender;
        this.messageCreator = messageCreator;
    }


    public void startGame(Game game){
        playerGame.put(game.getPlayer(1), game);
        playerGame.put(game.getPlayer(2), game);

        game.setState(Game.GameState.STARTED);

        Random rnd = new Random();
        boolean player1Starting = rnd.nextBoolean();

        sender.sendMessage(game.getPlayer(1), messageCreator.startGame( player1Starting ));
        sender.sendMessage(game.getPlayer(1), messageCreator.startGame( !player1Starting ));
    }


    public void dataRecieved( Player player, String dataMsg ){
        Game game = playerGame.get(player);
        Player opponent = game.getOpponent(player);
        if(opponent != null ){
            sender.sendMessage(opponent, dataMsg );
        }
    }


    private void stopGame(Game game){
        Player player = game.getPlayer(1);
        if(player != null){
            playerGame.remove(player);
        }
        player = game.getPlayer(2);
        if( player != null){
            playerGame.remove(player);
        }
    }


    public void removePlayer(Player player){
        Game game = playerGame.get(player);

        if( game != null ){
            Player opponent = game.getOpponent(player);
            sender.sendMessage(opponent, messageCreator.opponentDisconnected());
            stopGame(game);
        }

    }



}
