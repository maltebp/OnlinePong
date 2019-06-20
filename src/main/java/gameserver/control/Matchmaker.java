package gameserver.control;

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
    private static final double RATING_DIFF_TIME_FACTOR = 4;
    private static final double INITIAL_RATING_WINDOW = 100;

    // List of players looking for a match
    private final HashMap<Player, MatchPlayer> matchPlayers = new HashMap<>();
    private final LinkedList<MatchPlayer> lookingForMatch = new LinkedList<>();


    Matchmaker(Sender sender, MatchController matchController){
        this.matchController = matchController;
        this.sender = sender;
        start(); // Starting thread
    }


    public void matchmake(){
        synchronized (lookingForMatch){
            // List of players we have found a match for
            LinkedList<MatchPlayer> matchedPlayers = new LinkedList<>();

            // Copying the list so we can remove players as we evaluate they can't be matched
            LinkedList<MatchPlayer> remainingPlayers = new LinkedList<>(lookingForMatch);


            for (MatchPlayer player : lookingForMatch) {
                if( remainingPlayers.remove(player) ){

                    MatchPlayer opponent = findMatch(player, remainingPlayers);
                    if( opponent != null ){
                        // A match has been found
                        remainingPlayers.remove(opponent);

                        matchedPlayers.add(player);
                        player.setMatchedOpponent(opponent);

                        matchedPlayers.add(opponent);
                        opponent.setMatchedOpponent(player);
                    }else{
                        player.incrementTimeWaited(MATCHMAKING_FREQ);
                    }
                }
            }

                /*  Removing Matched players from the list of players
                    looking for a match, and sending correct message.
                    Can't do this in the other loop. */
            for(MatchPlayer player : matchedPlayers){
                lookingForMatch.remove(player);
                player.setHasAcceptedMatch(false);
                sender.sendFoundGame(player.getPlayer(), player.getMatchedOpponent().getPlayer());
            }
        }
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
                matchmake();
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
     * @param opponents A list of possible opponents for the Player
     */
    public MatchPlayer findMatch(MatchPlayer player, List<MatchPlayer> opponents) {
        /*  How it works:
            It checks each available opponentName for the player ('opponents')
            The rating difference between the player and the BEST opponentName
            fulfills following criteria:
                - It's less than player window
                - It's less than opponentName window
                - It's the smallest difference of all possible
                    opponents for player
            The window for a player is increased everytime it runs the algorithm
            without finding a match.
         */

        player.incrementRatingWindow(RATING_DIFF_TIME_FACTOR*MATCHMAKING_FREQ);

        MatchPlayer bestOpponent = null;
        double bestOpponentRatingDiff = 0;

        for (MatchPlayer opponent : opponents) {
            double ratingDiff = player.getRatingDifference(opponent);

            if (ratingDiff < player.getRatingWindow() && ratingDiff<opponent.getRatingWindow() && (bestOpponent == null || bestOpponentRatingDiff > ratingDiff)) {
                bestOpponent = opponent;
                bestOpponentRatingDiff = ratingDiff;
            }
        }

        return bestOpponent;
    }


    /**
     * Signals that a Player accepts a match.
     * Initializes the match if both Players have accepted.
     */
    synchronized void playerAcceptsMatch(Player player){
        MatchPlayer matchPlayer = matchPlayers.get(player);
        if( matchPlayer.getMatchedOpponent() != null ){
            matchPlayer.setHasAcceptedMatch(true);
            if( matchPlayer.getMatchedOpponent().hasAcceptedMatch() ){
                matchPlayers.remove(player);
                matchPlayers.remove(matchPlayer.getMatchedOpponent().getPlayer());
                matchController.startMatch(player, matchPlayer.getMatchedOpponent().getPlayer());
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
        MatchPlayer matchmakingPlayer = new MatchPlayer(player);
        matchPlayers.put(player, matchmakingPlayer);
        sender.sendFindingGame(player, 0);
        synchronized (lookingForMatch){
            lookingForMatch.add(matchmakingPlayer);
        }
    }


    /**
     * Removes a Player from the list of players looking
     * for a match.
     *
     * Used if the Player disconnects or if the Player
     * doesn't accept a match.
     * Will add the Player's opponentName to matchmaking again
     * and send a "findingGame" to the player.
     */
    void removePlayer(Player player){
        MatchPlayer matchPlayer = matchPlayers.get(player);
        if(matchPlayer != null ){
            synchronized (lookingForMatch) {
                if (!lookingForMatch.remove(matchPlayer)) {
                    MatchPlayer opponent = matchPlayer.getMatchedOpponent();
                    if (opponent != null) {
                        opponent.setMatchedOpponent(null);
                        opponent.setHasAcceptedMatch(false);
                        lookingForMatch.add(opponent);
                        sender.sendFindingGame(opponent.getPlayer(), 0);
                    }
                }
            }
        }
    }


    /**
     * A wrapper class for a Player object and additional information
     * about the Player only needed for matchmaking
     */
    private class MatchPlayer {
        private Player player;

        // The window of difference allowed between this player and other players to be matched
        private double ratingWindow = INITIAL_RATING_WINDOW;
        private boolean hasAcceptedMatch = false;
        private double timeWaited = 0;
        private MatchPlayer matchedOpponent;


        MatchPlayer(Player player ){
            this.player = player;
        }


        void incrementRatingWindow( double rating ){
            ratingWindow += rating;
            ratingWindow = (ratingWindow > MAX_RATING_DIFF) ? MAX_RATING_DIFF : ratingWindow;
        }

        void incrementTimeWaited(double time){
            timeWaited += time;
        }

        double getRatingWindow(){
            return ratingWindow;
        }

        double getRatingDifference(MatchPlayer opponent){
            double playerRating = player.getRating();
            double opponentRating = opponent.getPlayer().getRating();
            return (opponentRating > playerRating) ? opponentRating - playerRating : playerRating - opponentRating;
        }

        void setHasAcceptedMatch(boolean flag){
            hasAcceptedMatch = flag;
        }

        boolean hasAcceptedMatch(){
            return hasAcceptedMatch;
        }

        public MatchPlayer getMatchedOpponent() {
            return matchedOpponent;
        }

        public void setMatchedOpponent(MatchPlayer matchedOpponent) {
            this.matchedOpponent = matchedOpponent;
        }

        Player getPlayer(){
            return player;
        }
    }
}
