package gameserver.control;

public interface RatingAlgorithm {

    /**
     * @param winnerRating Rating of the winner of a match, before a match is started
     * @param loserRating Rating of the loser of a match before a match is started
     * @return The change to the losers's rating
     */
    int calculateLoserChange( double winnerRating, double loserRating );

    /**
     * @param winnerRating Rating of the winner of a match, before a match is started
     * @param loserRating Rating of the loser of a match before a match is started
     * @return The change to the winner's rating
     */
    int calculateWinnerChange( double winnerRating, double loserRating );

}
