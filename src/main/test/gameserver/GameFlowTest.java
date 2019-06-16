package gameserver;

import gameserver.model.Player;
import gameserver.testobjects.ClientConnector;
import gameserver.testobjects.ClientState;
import gameserver.testobjects.ServerConnector;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import static java.lang.Thread.sleep;

public class GameFlowTest {


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


    @Test
    public void testGameFinishSuccess() throws InterruptedException{
        ClientConnector kristian = connectPlayer("Kristian", 1000);
        ClientConnector simon = connectPlayer("Simon", 1000);

        sleep(3500);

        // Signal opponent won game
        kristian.sendMessage("{ \"code\" : 011 }");

        assertSame(ClientState.LOST_GAME, kristian.getState());
        assertSame(ClientState.WON_GAME, simon.getState());
    }


    @Test
    public void testGameFinishDisconnect() throws InterruptedException{
        ClientConnector kristian = connectPlayer("Kristian", 1000);
        ClientConnector simon = connectPlayer("Simon", 1000);

        sleep(3500);

        // Disconnect
        kristian.closeConnection();

        assertSame(ClientState.OPPONENT_DISC, simon.getState());
    }

    @Test
    public void testDoubleMatch() throws InterruptedException{
        ClientConnector kristian = connectPlayer("Kristian", 1000);
        ClientConnector simon = connectPlayer("Simon", 1000);
        ClientConnector claes = connectPlayer("Claes", 1000);

        sleep(3500);

        assertSame(ClientState.IN_GAME, kristian.getState());
        assertSame(ClientState.IN_GAME, simon.getState());
        assertSame(ClientState.WAITING_FOR_GAME, claes.getState());

        // Disconnect
        kristian.closeConnection();

        assertSame(ClientState.OPPONENT_DISC, simon.getState());

        // Kristian re-enter
        kristian.findGame();

        sleep(3500);

        assertSame(ClientState.IN_GAME, kristian.getState());
        assertSame(ClientState.OPPONENT_DISC, simon.getState());
        assertSame(ClientState.IN_GAME, claes.getState());

        // Signal opponent won game
        claes.sendMessage("{ \"code\" : 011 }");

        assertSame(ClientState.WON_GAME, kristian.getState());
        assertSame(ClientState.LOST_GAME, claes.getState());

        kristian = connectPlayer("Kristian", 1000);
    }
}
