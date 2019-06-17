package gameserver.control;

import gameserver.control.databaseconnector.DatabaseConnector;
import gameserver.control.ratingalgorithm.EloAlgorithm;
import gameserver.control.ratingalgorithm.RatingAlgorithm;
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
    private RatingAlgorithm ratingAlgorithm = new EloAlgorithm();
    private DatabaseConnector databaseConnector;

    // A map of Players and the match they participate in
    private HashMap<Player, Match> playerGame = new HashMap<Player, Match>();

    MatchController(Sender sender, DatabaseConnector databaseConnector){
        this.databaseConnector = databaseConnector;
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
     * Player participating in a match to the opponentName
     * of that player.
     */
    void dataRecieved(Player player, String dataMsg ){
        Match match = playerGame.get(player);
        Player opponent = match.getOpponent(player);
        if(opponent != null ){
            sender.sendMessage(opponent, dataMsg );
        }
    }


    /**
     * @param loser Losing player sending message
     */
    Player matchFinished(Player loser, boolean disconnected) {
        Match match = playerGame.get(loser);
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

                databaseConnector.updateElo(loser);
                databaseConnector.updateElo(winner);

                playerGame.remove(loser);
                playerGame.remove(winner);

                if(disconnected){
                    sender.sendOpponentDisconnected(winner, winnerRatingChange, loserRatingChange);
                }else{
                    sender.sendGameFinished(loser, false, loserRatingChange, winnerRatingChange);
                    sender.sendGameFinished(winner, true, winnerRatingChange, loserRatingChange );
                    return winner;
                }

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
        Match match = playerGame.get(player);
        if( match != null ) {
            matchFinished(player, true);
        }
    }


    public void setDatabaseConnector(DatabaseConnector databaseConnector){
        this.databaseConnector = databaseConnector;
    }
}
