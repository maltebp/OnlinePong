package gameserver.control;

import gameserver.model.Match;
import gameserver.model.Player;
import gameserver.view.Sender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


/**
 * Controller which handles the process of finding
 * a match for Players.
 *
 * It runs as a seperate threads, which checks for
 * possible matches between players in an constant
 * interval (MATCHMAKING_FREQ).
 *
 * Thread is initialized when Matchmaker is created.
 */
public class Matchmaker extends Thread{

    private Sender sender;
    private MatchController matchController;

    // The frequent between checking if there are any players to be matched
    private static final double MATCHMAKING_FREQ = 3;
    private static final double MAX_RATING_DIFF = 500;
    private static final double RATING_DIFF_TIME_FACTOR = 2;

    // List of players looking for a match
    private LinkedList<Player> lookingForMatch = new LinkedList<>();

    // List of players the matchmaker is waiting match acceptenance from (msg code 002)
    private HashMap<Player, Match> awaitingAccept = new HashMap<>();



    Matchmaker(Sender sender, MatchController matchController){
        this.matchController = matchController;
        this.sender = sender;
        start(); // Starting thread
    }


    /**
     * The loop which checks for possible matches
     * between players.
     * Initial method of the Thread.
     */
    @Override
    public void run(){
        try {

            while (true) {
                LinkedList<Match> newMatches = new LinkedList<Match>();
                LinkedList<Player> remainingPlayers = new LinkedList<>(lookingForMatch);

                for (Player player : lookingForMatch) {
                    if( remainingPlayers.remove(player) ){
                        Player opponent = findMatch(player, 0, remainingPlayers);
                        if( opponent != null ){
                            newMatches.add( new Match(player, opponent));
                            remainingPlayers.remove(opponent);
                        }
                    }
                }

                for(Match match : newMatches){
                    lookingForMatch.remove(match.getPlayer(1));
                    lookingForMatch.remove(match.getPlayer(2));
                    awaitingAccept.put(match.getPlayer(1), match);
                    awaitingAccept.put(match.getPlayer(2), match);
                    sender.sendFoundGame(match.getPlayer(1));
                    sender.sendFoundGame(match.getPlayer(2));
                }

                sleep((long) MATCHMAKING_FREQ * 1000);
            }
        }catch( InterruptedException e){
            System.out.println("Matchmaking thread is interrupted: "+e.getMessage());
        }
    }


    /**
     * Evaluate if the Player should be matched against any of the possible
     * opponents.
     *
     * @param timeWaited The time period (seconds) the Player has waited for a match (NOT IMPLEMENTED)
     * @param opponents A list of possible opponents for the Player
     */
    private Player findMatch(Player player, int timeWaited, List<Player> opponents) {
        double playerRating = player.getRating();
        double allowedRatingDiff = timeWaited * RATING_DIFF_TIME_FACTOR;

        // Adjusting difference to limit
        allowedRatingDiff = (allowedRatingDiff > MAX_RATING_DIFF) ? MAX_RATING_DIFF : allowedRatingDiff;

        Player bestOpponent = null;
        double bestOpponentRatingDiff = -1;
        double ratingDiff;
        double opponentRating;

        for (Player opponent : opponents) {
            opponentRating = opponent.getRating();
            ratingDiff = (opponentRating > playerRating) ? opponentRating - playerRating : playerRating - opponentRating;

            if (ratingDiff < allowedRatingDiff && (bestOpponentRatingDiff == -1 || bestOpponentRatingDiff > opponentRating)) {
                bestOpponent = opponent;
                bestOpponentRatingDiff = opponentRating;
            }
        }

        return bestOpponent;
    }


    /**
     * Signals that a Player accepts a match.
     * Initializes the match if both Players have accepted.
     */
    void playerAcceptsMatch(Player player){
        Match match = awaitingAccept.get(player);
        if( match != null ){
            match.playerAccepts(player);
            if(match.playersAccepted()){
                matchController.startMatch(match);
            }
        }else{
            // TODO: Implement error
        }
    }


    /**
     * Adds a Player to the list of players looking for
     * a match.
     */
    void addPlayer(Player player){
        lookingForMatch.add(player);
        sender.sendFindingGame(player, 0);
    }


    /**
     * Removes a Player from the list of players looking
     * for a match.
     *
     * Used if the Player disconnects or if the Player
     * doesn't accept a match.
     */
    void removePlayer(Player player){
        if( !lookingForMatch.remove(player) ){
            Match match = awaitingAccept.remove(player);
            if( match != null ){
                Player opponent = match.getOpponent(player);
                awaitingAccept.remove(player);
                awaitingAccept.remove(opponent);
                addPlayer(opponent);
            }
        }

    }


}
