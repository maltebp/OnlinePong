package gameserver.control;

public class EloAlgorithm implements RatingAlgorithm {

    private static final double ELO_K = 30; //The maximum a value may change

    @Override
    public int calculateLoserChange(double winnerRating, double loserRating) {
        double loserChance = chanceToWin(loserRating, winnerRating);
        System.out.println("Loser: "+loserChance);
        double ratingChange =  ELO_K * ( 0 - loserChance );
        System.out.println(ratingChange);
        return (int) Math.ceil(ratingChange);
    }

    @Override
    public int calculateWinnerChange(double winnerRating, double loserRating) {
        double winnerChance = chanceToWin(winnerRating, loserRating);
        System.out.println("Winner: "+winnerChance);
        double ratingChange = ELO_K * ( 1 - winnerChance );
        System.out.println(ratingChange);
        return (int) Math.ceil(ratingChange);
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
