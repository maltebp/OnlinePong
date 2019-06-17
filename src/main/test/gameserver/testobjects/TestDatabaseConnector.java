package gameserver.testobjects;

import gameserver.control.databaseconnector.DatabaseConnector;
import gameserver.model.Player;

public class TestDatabaseConnector implements DatabaseConnector {

    @Override
    public void setPlayerInformation(Player player) {

    }

    @Override
    public boolean authenticatePlayer(String username, String password) {
        return true;
    }

    @Override
    public void updateElo(Player player) {

    }
}
