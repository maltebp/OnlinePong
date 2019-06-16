package gameserver;

import gameserver.model.Player;
import gameserver.testobjects.ClientConnector;
import gameserver.testobjects.ClientState;
import gameserver.testobjects.ServerConnector;
import gameserver.testobjects.TestDatabaseConnector;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Client;

import static org.junit.Assert.*;

import static java.lang.Thread.sleep;

public class GameFlowTest {


    private ServerConnector connector;

    @Before
    public void players(){
        connector = new ServerConnector();
    }

    private ClientConnector connectPlayer(String username, String password, int rating){
        ClientConnector connection = new ClientConnector(username, password, rating, connector);
        connection.findGame();
        return connection;
    }


    @Test
    public void testGameFinishSuccess() throws InterruptedException{
        connector.gameServer.setDatabaseConnector(new TestDatabaseConnector());

        ClientConnector kristian = connectPlayer("Kristian", "pass", 1000);
        ClientConnector simon = connectPlayer("Simon", "pass", 1000);

        sleep(3500);

        // Signal opponent won game
        kristian.sendMessage("{ \"code\" : 011 }");

        assertSame(ClientState.LOST_GAME, kristian.getState());
        assertSame(ClientState.WON_GAME, simon.getState());
    }


    @Test
    public void testGameFinishDisconnect() throws InterruptedException{
        connector.gameServer.setDatabaseConnector(new TestDatabaseConnector());

        ClientConnector kristian = connectPlayer("Kristian","pass", 1000);
        ClientConnector simon = connectPlayer("Simon", "pass",1000);

        sleep(3500);

        // Disconnect
        kristian.closeConnection();

        assertSame(ClientState.OPPONENT_DISC, simon.getState());
    }

    @Test
    public void testDoubleMatch() throws InterruptedException{
        connector.gameServer.setDatabaseConnector(new TestDatabaseConnector());

        ClientConnector kristian = connectPlayer("Kristian", "pass",1000);
        ClientConnector simon = connectPlayer("Simon", "pass",1000);
        ClientConnector claes = connectPlayer("Claes", "pass",1000);

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
    }

    @Test
    public void testWrongUserNamePass() throws Exception{

        ClientConnector malte = connectPlayer("malte", "pandekag", 0);

        sleep(3000);
        assertSame(ClientState.IDLE, malte.getState());

        malte.closeConnection();

        sleep(3000);

        malte = connectPlayer("malte", "pandekage", 0 );
        assertSame(ClientState.WAITING_FOR_GAME, malte.getState());
    }


}
