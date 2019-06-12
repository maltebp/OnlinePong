package gameserver.model;

public class Match {

    private Player player1;
    private Player player2;

    private boolean player1Accepted = false;
    private boolean player2Accepted = false;

    private MatchState state = MatchState.ACCEPT_PENDING;

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


    public Player getPlayer(int index){
        return (index == 1) ? player1 : player2;
    }

    public void playerAccepts(Player player){
        if(player == player1){
            player1Accepted = true;
        }else{
            player2Accepted = true;
        }
    }


    public boolean playersAccepted(){
        return player1Accepted && player2Accepted;
    }


    public MatchState getState() {
        return state;
    }

    // TODO: Consider removing this
    public void setState(MatchState state) {
        this.state = state;
    }


    public enum MatchState {
        ACCEPT_PENDING,
        STARTED
    }

}
