package gameserver.model;

//todo: omnavngiv til DAO
/**
 * Defines a Match between two players
 */
public class Match {

    private Player player1;
    private Player player2;

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * @author Malte
     * @param player The player of which you want the opponentName
     * @return The player in the game suite which is not the parameter player.
     */
    public Player getOpponent(Player player){
        return (player == player1) ? player2 : player1;
    }

}
