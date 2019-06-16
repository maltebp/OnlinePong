package gameserver;

import gameserver.control.databaseconnector.APIConnector;
import gameserver.model.Player;

public class APIConnectorTest {

    public static void main(String[] args) {

        Player malte = new Player();

        malte.setUsername("Andreas");
        APIConnector con = new APIConnector();

        System.out.println("authendicate Player");
        con.authenticatePlayer("Andreas","123");

        System.out.println("setPlayerInformation");
        con.setPlayerInformation(malte);
        malte.setRating(900);

        System.out.println("updateElo");
        con.updateElo(malte);

        System.out.println("setPlayerInformation");
        con.setPlayerInformation(malte);
    }
}
