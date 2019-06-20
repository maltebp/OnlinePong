import java.io.File;
import java.net.BindException;

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
    private static final short PORT  = 8080;

    public static void main(String[] args) throws LifecycleException  {

        Tomcat tomcat;

        if (USE_ARRAY_DB) UserController.useBackup();

        tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(PORT);
        tomcat.addWebapp("", new File("src/main/webapp").getAbsolutePath());

        tomcat.getConnector();

        tomcat.start();

        System.out.println("\n\n" +
                "If you didn't get an exception during start-up, the TomCat server has started successfully," +
                "and all applications are ready!" +
                "\n\n" +
                "Go to 'localhost:8080' in your browser!");

        tomcat.getServer().await();

    }
}
