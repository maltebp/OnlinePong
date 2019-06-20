package gameserver.testobjects;

import gameserver.control.databaseconnector.DatabaseConnector;
import gameserver.model.Player;

public class TestDatabaseConnector implements DatabaseConnector {

    @Override
    public void getPlayerInformation(Player player) {

    }

    @Override
    public boolean authenticatePlayer(String username, String password) {
        return true;
    }

    @Override
    public void updateElo(Player player) {

    }
}
