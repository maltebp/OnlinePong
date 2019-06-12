package gameserver;

import javax.websocket.*;
import javax.websocket.server.*;
import org.json.*;

import java.io.IOException;

@ServerEndpoint("/communication")
public class API {


    private static MessageController messageController = new MessageController();


    @OnOpen
    public void onOpen(Session session ){
        try {
            session.getBasicRemote().sendText("A connection has been made");
            System.out.println("Recieved open request");
        }catch(IOException e){
            e.getMessage();
        }
    }

//TODO Lukke, forbindelsen, hvis at en bruger taster et forkert login.
    @OnMessage
    public void onMessage(Session session, String msg ){
        JSONObject obj = new JSONObject(msg);
        messageController.evaluateMessage(session, obj);
    }

    @OnClose
    public void onClose(Session session){

    }

}
