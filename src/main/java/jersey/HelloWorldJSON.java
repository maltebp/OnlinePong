package jersey;


import com.google.gson.Gson;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.LinkedList;

import static javafx.application.ConditionalFeature.MEDIA;


@Path("/json")
public class HelloWorldJSON {

    static int ballId2 = 0;

    @GET
    public String getMessage(
            @DefaultValue("111")
            @QueryParam("ball") Integer ballId, @Context UriInfo uriInfo) {

        ballId2++;
        ballId2 = ballId2 == 2 ? 0 : ballId2;

        System.out.println(ballId);
        Gson json = new Gson();
        Ball[] ball = new Ball[2];
        ball[0] = new Ball(10, 20, 10, 50);
        ball[1] = new Ball(15, 5, 20, 50);
        return json.toJson(ball[ballId2]); //ball.toString();
    }

    @POST

    //@Consumes(MediaType.TEXT_XML)
    public String postStrMsg(String msg) {
        String output = "POST:Jersey say : " + msg;
        return output;
    }


   /* @POST

    @Consumes(MediaType.APPLICATION_JSON)
    public String postBall(String str) {



    }*/

}


