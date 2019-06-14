package gameserver;

import gameserver.control.EloAlgorithm;
import org.junit.Test;
import static org.junit.Assert.*;

public class EloAlgorithmTest {

    @Test
    public void testRatingChange(){
        EloAlgorithm algorithm = new EloAlgorithm();

        int[][] matchData = {
                {500,500,15,15},
                {400,400,15,15},
                {1000,1000,15,15},
                {1400,1400,15,15},
                {1500,1500,15,15},
                {2400,2400,15,15},
                {500,2400,15,15},
                {2400,500,15,15},
        };

    }


    @Test
    public void testRatingChangeSum(){

        EloAlgorithm algorithm = new EloAlgorithm();

        for(int winnerRating=200; winnerRating<2400; winnerRating++){
            System.out.println("=====================================\n"+winnerRating );
            for(int loserRating=200; loserRating<2400; loserRating++){
                int winnerChange = algorithm.calculateWinnerChange(winnerRating, loserRating);
                int loserChange = algorithm.calculateLoserChange(winnerRating, loserRating);
                System.out.println("\t"+loserRating+":  "+winnerChange+" / "+loserChange);
                assertEquals( 30, winnerChange+ (-1*loserChange));
            }
        }



    }
}
