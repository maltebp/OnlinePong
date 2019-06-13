package gameserver;

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
        TestPlayer andreas = connectPlayer("Andreas", 1150);
        TestPlayer simon = connectPlayer("Simon", 1500);

        sleep(10000);
        assertTrue(!jacob.isMatched);
        assertTrue(!andreas.isMatched);
        assertTrue(!simon.isMatched);

        sleep(6000);

        assertTrue(jacob.isMatched);
        assertTrue(andreas.isMatched);
        assertTrue(!simon.isMatched);
    }


    /**
     * Test:
     * If Player 1's window allows him be matched with Player 2
     * but Player 2's window doesn't allow him to be matched
     * with Player 1, they shouldn't be matched.
     */
    @Test
    public void testSeperateWindows() throws InterruptedException{

        TestPlayer jacob = connectPlayer("Jacob", 1000);

        sleep(10000);
        // Jacobs window should now be 36, allowing him to match up with Andreas

        TestPlayer andreas = connectPlayer("Andreas", 1130);

        sleep(4000);
        assertTrue(!jacob.isMatched);
        assertTrue(!andreas.isMatched);

        sleep(10000);
        // Andreas' window should now match jacob
        assertTrue(jacob.isMatched);
        assertTrue(andreas.isMatched);
    }
}
