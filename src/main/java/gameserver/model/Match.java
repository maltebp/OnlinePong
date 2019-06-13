package gameserver.model;


/**
 * Defines a Match between two players
 */
public class Match {

    private Player player1;
    private Player player2;

    private boolean player1Accepted = false;
    private boolean player2Accepted = false;

    public Match(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
    }

    /**
     * @author Malte
     * @param player The player of which you want the opponent
     * @return The player in the game suite which is not the parameter player.
     */
    public Player getOpponent(Player player){
        return (player == player1) ? player2 : player1;
    }


    /**
     * @param index 1 or 2 (Player 1 or Player 2)
     */
    public Player getPlayer(int index){
        return (index == 1) ? player1 : player2;
    }


    /**
     * Sets that a given player has accepted this match
     * Will do nothing if the player is not either Player 1
     * or 2.
     */
    public void playerAccepts(Player player){
        if(player == player1){
            player1Accepted = true;
        }else{
            player2Accepted = true;
        }
    }


    /**
     * @return True if both players has accepted the match
     *          (signalled by playerAccepts())
     */
    public boolean playersAccepted(){
        return player1Accepted && player2Accepted;
    }
}
