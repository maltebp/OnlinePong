package gameserver;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Matchmaker extends Thread{

    private static final double MATCHMAKING_UPDATE = 3; // Seconds
    private Sender sender;

    private ArrayList<Player> lookingForGame = new ArrayList<Player>();
    private GameController gameController;
    private HashMap<Player, Game> awaitingAccept = new HashMap<Player, Game>();
    private MessageCreator messageCreator;

    public Matchmaker( Sender sender, GameController gameController, MessageCreator messageCreator){
        this.gameController = gameController;
        this.sender = sender;
        this.messageCreator = messageCreator; // TODO: Move message creator funcitonality into sender
        start();
    }


    public void run(){
        try {
            while (true) {
                LinkedList<Game> newGames = new LinkedList<Game>();
                Player player1 = null;
                for (Player player : lookingForGame) {
                    if (player1 == null) {
                        player1 = player;
                    } else {
                        newGames.add(new Game(player1, player));
                        player1 = null;
                    }
                }
                for(Game game : newGames){
                    lookingForGame.remove(game.getPlayer(1));
                    lookingForGame.remove(game.getPlayer(2));
                    awaitingAccept.put(game.getPlayer(1), game);
                    awaitingAccept.put(game.getPlayer(2), game);
                }
                sleep((long) 3 * 1000);
            }
        }catch( InterruptedException e){
            System.out.println("Matchmaking thread is interrupted: "+e.getMessage());
        }
    }


    public void playerAcceptsMatch(Player player){
        Game game = awaitingAccept.get(player);
        if( game != null ){
            game.playerAccepts(player);
            if(game.playersAccepted()){
                gameController.startGame(game);
            }
        }else{
            // TODO: Implement error
        }
    }

    public void addPlayer(Player player){
        lookingForGame.add(player);
        sender.sendMessage(player, messageCreator.findingGame(0));
    }


    public void removePlayer(Player player){
        if( !lookingForGame.remove(player) ){
            Game game = awaitingAccept.remove(player);
            if( game != null ){
                Player opponent = game.getOpponent(player);
                awaitingAccept.remove(player);
                awaitingAccept.remove(opponent);
                addPlayer(opponent);
            }
        }

    }


}
