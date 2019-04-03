package jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.json.*;

@Path("/json")
public class HelloWorldJSON {


    @GET
    public String getMessage(){
        //Ball ball = new Ball(10, 20);

        JSONObject json = new JSONObject();

        json.put("msg", "hello world");

        return json.toString();
    }



}
