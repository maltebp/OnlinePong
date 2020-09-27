package api.services;

import api.database.mongodb.MongoDatabase;
import io.javalin.Javalin;
import io.javalin.http.Context;
import server.Server;

public class TestModeService {

    public TestModeService(Javalin server){
        server.put("api/testmode/clear", this::clearData);
    }

    public void clearData(Context context){
        if( !Server.isTestMode() ){
            Response.setError(context, 403, "Cannot clear data when not in test mode");
            return;
        }

        MongoDatabase.useNewTestDatabase();
        Response.setError(context, 200, "All data was cleared");

    }
}
