package gameserver.control.ratingalgorithm;

public interface RatingAlgorithm {

    /**
     * @param winnerRating Rating of the winner of a match, before a match is started
     * @param loserRating Rating of the loser of a match before a match is started
     * @return The change to the losers's rating
     */
    int calculateLoserChange( int winnerRating, int loserRating );

    /**
     * @param winnerRating Rating of the winner of a match, before a match is started
     * @param loserRating Rating of the loser of a match before a match is started
     * @return The change to the winner's rating
     */
    int calculateWinnerChange( int winnerRating, int loserRating );

}
