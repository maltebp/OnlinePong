package gameserver.control;


import gameserver.model.Match;
import gameserver.model.Player;
import gameserver.view.Sender;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Matchmaker extends Thread{

    // The frequent between checking if there are any players to be matched
    private static final double MATCHMAKING_FREQ = 3;
    private Sender sender;

    private LinkedList<Player> lookingForGame = new LinkedList<>();
    private MatchController matchController;
    private HashMap<Player, Match> awaitingAccept = new HashMap<>();
;

    public Matchmaker(Sender sender, MatchController matchController){
        this.matchController = matchController;
        this.sender = sender;
        start();
    }


    public void run(){
        try {
            while (true) {
                LinkedList<Match> newMatches = new LinkedList<Match>();
                LinkedList<Player> remainingPlayers = new LinkedList<>(lookingForGame);

                for (Player player : lookingForGame) {
                    if( remainingPlayers.remove(player) ){
                        Player opponent = findMatch(player, 0, remainingPlayers);
                        if( opponent != null ){
                            newMatches.add( new Match(player, opponent));
                            remainingPlayers.remove(opponent);
                        }
                    }
                }

                for(Match match : newMatches){
                    lookingForGame.remove(match.getPlayer(1));
                    lookingForGame.remove(match.getPlayer(2));
                    awaitingAccept.put(match.getPlayer(1), match);
                    awaitingAccept.put(match.getPlayer(2), match);
                    sender.sendFoundGame(match.getPlayer(1));
                    sender.sendFoundGame(match.getPlayer(2));
                }

                sleep((long) MATCHMAKING_FREQ * 1000);
            }
        }catch( InterruptedException e){
            System.out.println("Matchmaking thread is interrupted: "+e.getMessage());
        }
    }


    /** The method which evalutes who the player should play against */
    protected Player findMatch(Player player, int timeWaited, List<Player> opponents){
        if( !opponents.isEmpty() ) return opponents.get(0);
        return null;
    }

    public void playerAcceptsMatch(Player player){
        Match match = awaitingAccept.get(player);
        if( match != null ){
            match.playerAccepts(player);
            if(match.playersAccepted()){
                matchController.startGame(match);
            }
        }else{
            // TODO: Implement error
        }
    }

    public void addPlayer(Player player){
        lookingForGame.add(player);
        sender.sendFindingGame(player, 0);
    }


    public void removePlayer(Player player){
        if( !lookingForGame.remove(player) ){
            Match match = awaitingAccept.remove(player);
            if( match != null ){
                Player opponent = match.getOpponent(player);
                awaitingAccept.remove(player);
                awaitingAccept.remove(opponent);
                addPlayer(opponent);
            }
        }

    }


}
