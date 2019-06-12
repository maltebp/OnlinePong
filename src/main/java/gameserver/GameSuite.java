package gameserver;

import java.io.IOException;

public class GameSuite {

    Player player1;
    Player player2;



    public GameSuite(Player player1, Player player2){
        this.player1 = player1;
        this.player2 = player2;
        player2.setSuite(this);
        player1.setSuite(this);

    }


    public void passMessage(String message, Player player){

      try {
          player.getSession().getBasicRemote().sendText(message);
      }catch(IOException e){
          e.getCause();
      }

    }

}
