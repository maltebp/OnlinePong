package gameserver;

import org.json.JSONException;
import org.json.JSONObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

class MessageController{

    private MessageCreator messageCreator = new MessageCreator();
    private PlayerController playerController = new PlayerController();
    private Matchmaker matchmaker = new Matchmaker();


    void evaluateMessage( Session session, JSONObject msg ){

        try{
            switch( msg.getInt("code")){

                // Login
                case 1:
                    String username = msg.getString("username");
                    String password = msg.getString("password");

                    if( Authenticator.authenticateUser(username, password) ) {
                        Player player = playerController.addPlayer(session, username);

                        if (player != null) {
                            Player opponent = matchmaker.findMatch(player, playerController.getPlayersLookingForGame());

                            if (opponent != null) {

                                // TODO: REMOVE THIS

                                new GameSuite(player, opponent);
                                playerController.setPlayersInGame(player, opponent);

                                sendMessage( player.getSession(), messageCreator.foundGame());
                                sendMessage( opponent.getSession(), messageCreator.foundGame());

                                sendMessage( player.getSession(), messageCreator.startGame(false));
                            } else {
                                sendMessage(session, messageCreator.findingGame(0));
                            }

                        }else{
                            System.out.println("Player already logged in");
                        }

                    }else {
                        sendMessage(session, messageCreator.wrongUsernamePassword());
                    }
                    break;

                case 10:
                    Player player = playerController.getPlayer(session);

                    Player opponent = (player.getSuite().player2 == player) ? player.getSuite().player1 : player.getSuite().player2;
                    sendMessage(opponent.getSession(), msg.toString());


                default:
                    sendMessage(session, messageCreator.wrongMessageFormat("Unknown message code"));
            }

        }catch(JSONException e){

            sendMessage(session, "{\"code\" : 204 }");
        }

    }


    private void sendMessage( Session session, String msg ){
        try{
            session.getBasicRemote().sendText(msg);
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
