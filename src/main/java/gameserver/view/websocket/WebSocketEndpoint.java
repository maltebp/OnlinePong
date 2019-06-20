package gameserver.view.websocket;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/gameserver")
public class WebSocketEndpoint {

    private static WebSocketController controller = new WebSocketController();

    @OnOpen
    public void onOpen(Session session){
        controller.openSession(session);
    }

    @OnMessage
    public void onMessage( Session session, String msg ){
        controller.messageRecieved(session, msg);
    }

    @OnClose
    public void onClose( Session session ){
        controller.closeSession(session);
    }

    @OnError
    public void onError(Throwable t){
        System.out.println("Some error occured: "+t.getMessage());
    }



}
