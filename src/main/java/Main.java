import java.io.File;

import API.Controller.UserController;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;


/**
 * Initializes the Tomcat Server on the localhost on
 * port 8080.
 *
 * The applications following applications are hosted on
 * this server:
 *  - Website
 *  - REST API
 *  - Game Server (websocket)
 *
 * USING ARRAYLIST:
 * Change the value of USE_ARRAY_DB to 'true';
 */
public class Main {

    private static final boolean USE_ARRAY_DB = false;

    public static void main(String[] args) throws LifecycleException  {

        Tomcat tomcat;

        if(USE_ARRAY_DB) UserController.useBackup();

        tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(8080);
        tomcat.getConnector();

        tomcat.addWebapp("",new File("src/main/webapp").getAbsolutePath());

        tomcat.start();

        tomcat.getServer().await();
    }
}
