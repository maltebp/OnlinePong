package gameserver;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Matchmaker extends Thread{

    // The frequent between checking if there are any players to be matched
    private static final double MATCHMAKING_FREQ = 3;
    private Sender sender;

    private LinkedList<Player> lookingForGame = new LinkedList<>();
    private GameController gameController;
    private HashMap<Player, Game> awaitingAccept = new HashMap<>();
    private MessageCreator messageCreator;
;

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
                LinkedList<Player> remainingPlayers = new LinkedList<>(lookingForGame);

                for (Player player : lookingForGame) {
                    if( remainingPlayers.remove(player) ){
                        Player opponent = findMatch(player, 0, remainingPlayers);
                        if( opponent != null ){
                            newGames.add( new Game(player, opponent));
                            remainingPlayers.remove(opponent);
                        }
                    }
                }

                for(Game game : newGames){
                    lookingForGame.remove(game.getPlayer(1));
                    lookingForGame.remove(game.getPlayer(2));
                    awaitingAccept.put(game.getPlayer(1), game);
                    awaitingAccept.put(game.getPlayer(2), game);
                }

                sleep((long) MATCHMAKING_FREQ * 1000);
            }
        }catch( InterruptedException e){
            System.out.println("Matchmaking thread is interrupted: "+e.getMessage());
        }
    }


    /** The method which evalutes who the player should play against */
    protected Player findMatch(Player player, int timeWaited, List<Player> opponents){
        if( !opponents.isEmpty() ) return opponents.get(0);
        return null;
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
