package gameserver.control.ratingalgorithm;


import gameserver.control.ratingalgorithm.RatingAlgorithm;

/**
 * ELO-RATING
 * Rating calculations based upon the Elo-Rating system,
 * as it's used for i.e. chess.
 *
 *      Source:
 *      https://en.wikipedia.org/wiki/Elo_rating_system [14-jun-2019]
 *
 * We don't adjust K, meaning players will always win the same
 * rating change at the same rating difference despite rating:
 *
 *          2400 vs 2400  -> Winnner: 15, Loser: -15
 *          200  vs 200   -> Winner: 15,  Loser: -15
 *
 */
public class EloAlgorithm implements RatingAlgorithm {

    private static final double ELO_K = 30; //The maximum a value may change


    @Override
    public int calculateLoserChange(int winnerRating, int loserRating) {
        double loserChance = chanceToWin(loserRating, winnerRating);
        double ratingChange =  ELO_K * ( 0 - loserChance );

        /* Checking that rating change won't make loser go below 0 rating
            if so, we just return a rating such that he'll end up at 0  */
        if( loserRating+ratingChange < 0 ){
            return -loserRating;
        }
        return (int) Math.floor(ratingChange); // To the next whole negative number (-14.3) -> (-15)
    }


    @Override
    public int calculateWinnerChange(int winnerRating, int loserRating) {
        double winnerChance = chanceToWin(winnerRating, loserRating);
        double ratingChange = ELO_K * ( 1 - winnerChance );
        return (int) Math.ceil(ratingChange); //  To the next whole positive number 14.3 -> 15
    }


    /**
     * Calculates the chance for a player to win against another
     * player considering the Elo-rating of the players.
     * @param playerRating Rating of the player you want to calculate winning chance for
     * @param opponentRating Rating of the opponenet of the player
     * @return The player's chance to win in decimal percent ( 0.00 - 1.00 )
     */
    private double chanceToWin( double playerRating, double opponentRating ){
        return 1 / (1 + Math.pow(10, (opponentRating-playerRating)/400) );
    }

}
