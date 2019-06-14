package gameserver;

import gameserver.testobjects.ClientConnector;
import gameserver.testobjects.ClientState;
import gameserver.testobjects.ServerConnector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;


import static java.lang.Thread.sleep;

public class MatchMakingTest {

    private ServerConnector connector;

    @Before
    public void players(){
        connector = new ServerConnector();
    }

    private ClientConnector connectPlayer(String username, int rating){
        ClientConnector connection = new ClientConnector(username, "pass", rating, connector);
        connection.findGame();
        return connection;
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


        ClientConnector jacob = connectPlayer("Jacob", 1000);
        ClientConnector andreas = connectPlayer("Andreas", 1150);
        ClientConnector simon = connectPlayer("Simon", 1500);

        sleep(10000);
        assertNotSame(jacob.getState(), ClientState.IN_GAME);
        assertNotSame(andreas.getState(), ClientState.IN_GAME);
        assertNotSame(simon.getState(), ClientState.IN_GAME);

        sleep(10000);

        assertSame(jacob.getState(), ClientState.IN_GAME);
        assertSame(andreas.getState(), ClientState.IN_GAME);
        assertNotSame(simon.getState(), ClientState.IN_GAME);
    }


    /**
     * Test:
     * If Player 1's window allows him be matched with Player 2
     * but Player 2's window doesn't allow him to be matched
     * with Player 1, they shouldn't be matched.
     */
    @Test
    public void testSeperateWindows() throws InterruptedException{

        ClientConnector jacob = connectPlayer("Jacob", 1000);

        sleep(10000);
        // Jacobs window should now be 36, allowing him to match up with Andreas

        ClientConnector andreas = connectPlayer("Andreas", 1130);

        sleep(4000);
        assertNotSame(jacob.getState(), ClientState.IN_GAME);
        assertNotSame(andreas.getState(), ClientState.IN_GAME);

        sleep(10000);
        // Andreas' window should now match jacob
        assertSame(jacob.getState(), ClientState.IN_GAME);
        assertSame(andreas.getState(), ClientState.IN_GAME);
    }
}
