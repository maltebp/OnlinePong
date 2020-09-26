import java.io.File;
import java.io.FileNotFoundException;

import keymanager.KeyManager;
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

    private static final short PORT  = 20000;

    public static void main(String[] args) throws LifecycleException, FileNotFoundException  {
        KeyManager.loadKeys();

        Tomcat tomcat;

        tomcat = new Tomcat();
        tomcat.setBaseDir("temp");
        tomcat.setPort(PORT);

        System.out.println("Working directory: " + System.getProperty("user.dir"));
        String webAppFolder = getWebAppFolder();
        System.out.println("Web app folder: " + webAppFolder);
        tomcat.addWebapp("", webAppFolder);

        tomcat.getConnector();

        tomcat.start();

        System.out.println("\n\n" +
                "If you didn't get an exception during start-up, the TomCat server has started successfully," +
                "and all applications are ready!" +
                "\n\n" +
                "Go to 'localhost:" + PORT + "' in your browser!");

        tomcat.getServer().await();

    }

    private static String getWebAppFolder() throws FileNotFoundException {
        String workingDir = System.getProperty("user.dir");
        File f;

        f = new File(workingDir + "/webapp");
        if (f.exists() && f.isDirectory()) {
            return f.getAbsolutePath();
        }

        f = new File("src/main/webapp");
        if (f.exists() && f.isDirectory()) {
            return f.getAbsolutePath();
        }

        f = new File("webapp");
        if (f.exists() && f.isDirectory()) {
            return f.getAbsolutePath();
        }

        throw new FileNotFoundException("Cannot find webapp folder");
    }



}
