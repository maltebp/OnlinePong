package gameserver;

import gameserver.control.EloAlgorithm;
import org.junit.Test;
import static org.junit.Assert.*;

public class EloAlgorithmTest {


    /**
     * Tests some constant test data
     */
    @Test
    public void testRatingChange(){
        EloAlgorithm algorithm = new EloAlgorithm();

        int[][] matchData = {
                {500,500,15,-15},
                {400,400,15,-15},
                {1000,1000,15,-15},
                {1400,1400,15,-15},
                {1500,1500,15,-15},
                {2400,2400,15,-15},
                {500,2400,30,-30},
                {2400,500,1,-1},
                {1600,1400,8,-8},
                {1400,1600,23,-23},
                {200,300,20,-20},
                {300,200,11,-11},
                {2400,2200,8,-8},
                {2200,2400,23,-23},
                {50,0,13,0},
                {50,10,14,-10},
        };

        for(int i=0; i<16; i++){
            int winnerChange = algorithm.calculateWinnerChange(matchData[i][0], matchData[i][1]);
            int loserChange = algorithm.calculateLoserChange(matchData[i][0], matchData[i][1]);
            assertEquals(matchData[i][2], winnerChange);
            assertEquals(matchData[i][3], loserChange);
        }
    }





    /**
     * Testing that as the rating change of the players are
     * always -x and x, when the losing player has enough
     * rating to loose (won't loose 15 rating if he only
     * has 13).
     */
    @Test
    public void testRatingChangeSimilarity(){

        EloAlgorithm algorithm = new EloAlgorithm();

        for(int winnerRating=30; winnerRating<5000; winnerRating++){
            for(int loserRating=30; loserRating<5000; loserRating++){
                int winnerChange = algorithm.calculateWinnerChange(winnerRating, loserRating);
                int loserChange = algorithm.calculateLoserChange(winnerRating, loserRating);
                assertEquals( -1*loserChange, winnerChange);
            }
        }
    }


    /**
     * Testing that the range of rating change is between
     * [0, -30] for losing player and [0, 30] for a winning
     * player.
     */
    @Test
    public void testRatingRange(){

        EloAlgorithm algorithm = new EloAlgorithm();

        for(int winnerRating=0; winnerRating<5000; winnerRating++){
            for(int loserRating=0; loserRating<5000; loserRating++){
                int winnerChange = algorithm.calculateWinnerChange(winnerRating, loserRating);
                int loserChange = algorithm.calculateLoserChange(winnerRating, loserRating);
                assertTrue(loserChange >= -30);
                assertTrue(winnerChange <= 30);
                assertTrue(winnerChange >= 0);
                assertTrue(loserChange <= 0);
            }
        }
    }



    /**
     * Testing that a players rating will never be calculated
     * a rating change which set the rating below 0.
     *
     * Only checking for loser rating up 30 because
     * you may never lose more rating than that.
     */
    @Test
    public void testNegativeRating(){

        EloAlgorithm algorithm = new EloAlgorithm();

        for(int rating=0; rating<=30; rating++){
            int winnerChange = algorithm.calculateWinnerChange( rating, rating);
            int loserChange = algorithm.calculateLoserChange(rating, rating);
            System.out.println(rating +":\t"+winnerChange + " / "+loserChange);
            assertTrue(rating+loserChange >= 0);
            assertTrue(winnerChange > 0);
        }

    }
}
