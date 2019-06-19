package gameserver.control;

import gameserver.control.ratingalgorithm.EloAlgorithm;
import gameserver.control.ratingalgorithm.RatingAlgorithm;
import gameserver.model.Match;
import gameserver.model.Player;

import java.util.HashMap;
import java.util.Random;


/**
 * Controller which handles an ONGOING match, initialized by
 * the Matchmaker.
 */
class MatchController {

    private GameServer server;
    private RatingAlgorithm ratingAlgorithm = new EloAlgorithm();

    // A map of Players and the match they participate in
    private HashMap<Player, Match> playerMatch = new HashMap<>();

    MatchController(GameServer server){
        this.server = server;
    }


    /**
     * Sets a match as started by adding it to the
     * ongoing match list (playerMatch)
     */
    void startMatch(Player player1, Player player2){
        Match match = new Match(player1, player2);
        playerMatch.put(player1, match);
        playerMatch.put(player2, match);

        // Randomize the initializing player
        Random rnd = new Random();
        boolean player1Starting = rnd.nextBoolean();

        server.getSender().sendStartGame(player1, player1Starting);
        server.getSender().sendStartGame(player2, !player1Starting);
    }


    /**
     * Forwards data recieved via message code 010 from a
     * Player participating in a match to the opponentName
     * of that player.
     */
    void dataRecieved(Player player, String dataMsg ){
        Match match = playerMatch.get(player);
        Player opponent = match.getOpponent(player);
        if(opponent != null ){
            server.getSender().sendMessage(opponent, dataMsg );
        }
    }


    /**
     * @param loser Losing player sending message
     */
    Player matchFinished(Player loser, boolean disconnected) {
        Match match = playerMatch.get(loser);
        if( match != null ){
            Player winner = match.getOpponent(loser);
            if(winner != null ){

                // Updating rating
                int loserRating = loser.getRating();
                int winnerRating = winner.getRating();

                int loserRatingChange = ratingAlgorithm.calculateLoserChange(winnerRating, loserRating);
                int winnerRatingChange = ratingAlgorithm.calculateWinnerChange(winnerRating, loserRating);

                loser.setRating( loserRating + loserRatingChange );
                winner.setRating(winnerRating + winnerRatingChange);


                playerMatch.remove(loser);
                playerMatch.remove(winner);

                if(disconnected){
                    server.getSender().sendOpponentDisconnected(winner, winnerRatingChange, loserRatingChange);
                }else{
                    server.getSender().sendGameFinished(loser, false, loserRatingChange, winnerRatingChange);
                    server.getSender().sendGameFinished(winner, true, winnerRatingChange, loserRatingChange );

                }

                server.getDatabaseConnector().updateElo(loser);
                server.getDatabaseConnector().updateElo(winner);

                if( !disconnected) return winner;

            }else{
                System.out.println("Error: player has no opponentName");
            }
        }else{
            System.out.println("Error: Player has no match!");
        }
        return null;
    }


    /**
     * Removes the player by checking if he/she
     * is in a match, and if so it will finish the map
     * with a disconnect signal
     */
    void removePlayer(Player player){
        Match match = playerMatch.get(player);
        if( match != null ) {
            matchFinished(player, true);
        }
    }
}
