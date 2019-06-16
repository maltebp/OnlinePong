package gameserver.control;

import gameserver.model.Match;
import gameserver.model.Player;
import gameserver.view.Sender;

import java.util.HashMap;
import java.util.Random;


/**
 * Controller which handles an ONGOING match, initialized by
 * the Matchmaker.
 */
class MatchController {

    private Sender sender;

    // A map of Players and the match they participate in
    private HashMap<Player, Match> playerGame = new HashMap<Player, Match>();

    MatchController(Sender sender){
        this.sender = sender;
    }


    /**
     * Sets a match as started by adding it to the
     * ongoing match list (playerGame)
     */
    void startMatch(Player player1, Player player2){
        Match match = new Match(player1, player2);
        playerGame.put(player1, match);
        playerGame.put(player2, match);

        // Randomize the initializing player
        Random rnd = new Random();
        boolean player1Starting = rnd.nextBoolean();

        sender.sendStartGame(player1, player1Starting);
        sender.sendStartGame(player2, !player1Starting);
    }


    /**
     * Forwards data recieved via message code 010 from a
     * Player participating in a match to the opponent
     * of that player.
     */
    void dataRecieved( Player player, String dataMsg ){
        //TODO: remote sout
        System.out.println("Datatecived: "+dataMsg);
        Match match = playerGame.get(player);
        Player opponent = match.getOpponent(player);
        if(opponent != null ){
            sender.sendMessage(opponent, dataMsg );
        }
    }


    /**
     * Signals that the opponent of a Player has won
     * the match.
     * Run by GameServer after recieving a 011 message
     * from losing player.
     *
     * @param player Losing player sending message
     * @param message Message containing code (possible extra information in the future)
     * @return The winning Player of the match
     */
    Player playerHasWon(Player player, String message) {
        Match match = playerGame.get(player);
        if( match != null ){
            playerGame.remove(player);
            Player opponent = match.getOpponent(player);
            if(opponent != null ){
                playerGame.remove(opponent);
                sender.sendMessage(opponent, message);
            }
        }
        return null;
    }
/*

    void adjustRating(Player winner, Player loser){

        Player winnerChance =

    }*/

    /**
     * Removes a Player from its match, if its
     * participating in one.
     *
     * Called when a player loses connection
     */
    void removePlayer(Player player){
        Match match = playerGame.get(player);

        if( match != null ){
            Player opponent = match.getOpponent(player);
            sender.sendOpponentDisconnected(opponent);
            stopMatch(match);
        }
    }


    /**
     * Stops the match, and disconnect players
     * still participating in the match.
     *
     * Is called by removePlayer()
     * @param match
     */
    private void stopMatch(Match match){
        Player player = match.getPlayer(1);
        if(player != null){
            playerGame.remove(player);
        }
        player = match.getPlayer(2);
        if( player != null){
            playerGame.remove(player);
        }
    }


}
