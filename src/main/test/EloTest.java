
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


import static java.lang.Thread.sleep;

public class EloTest {

    private TestConnector connector;

    @Before
    public void players(){
        connector = new TestConnector();
    }

    private TestPlayer connectPlayer(String username, int rating){
        TestPlayer player = new TestPlayer();
        player.setUsername(username);
        player.setRating(rating);
        connector.recieveMessage(player, "{ \"code\" : 1, \"username\":\"" + username + "\", \"password\":\"somepassword\"}" );
        return player;
    }


    /**
     * Tests that the matchmaker becomes more "loose" about
     * the rating difference.
     *
     * It should take 18 seconds for Andreas and Jacob to be
     * matched, and Simon shouldn't be matched at all.
     */
    @Test
    public void testMatchmaking() throws InterruptedException{

        TestPlayer jacob = connectPlayer("Jacob", 1000);
        TestPlayer andreas = connectPlayer("Andreas", 1050);
        TestPlayer simon = connectPlayer("Simon", 1100);

        sleep(15000);
        assertTrue(!jacob.isMatched);
        assertTrue(!andreas.isMatched);
        assertTrue(!simon.isMatched);

        sleep(3000);

        assertTrue(jacob.isMatched);
        assertTrue(andreas.isMatched);
        assertTrue(!simon.isMatched);
    }
}
