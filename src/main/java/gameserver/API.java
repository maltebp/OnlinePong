package gameserver;

import javax.websocket.*;
import javax.websocket.server.*;
import org.json.*;

@ServerEndpoint("/communication")
public class API {


    private static MessageController messageController = new MessageController();


    @OnOpen
    public void onOpen(Session session ){
        System.out.println("Recieved open request");
    }


    @OnMessage
    public void onMessage(Session session, String msg ){
        JSONObject obj = new JSONObject(msg);
        messageController.evaluateMessage(session, obj);
    }

    @OnClose
    public void onClose(Session session){

    }

}
