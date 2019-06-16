package gameserver.control;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.SQLOutput;

public class Decoder {


    public HttpURLConnection createConnection(URL urlForPOSTRequest, String requestType) {
        try {
            HttpURLConnection connection = (HttpURLConnection) urlForPOSTRequest.openConnection();


            //Designing the request
            connection.setRequestMethod(requestType);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");

            //Enable writing to outputStream
            connection.setDoOutput(true);
            return connection;

        } catch (ProtocolException x) {
            x.printStackTrace();

        } catch (IOException e) {
            e.printStackTrace();


        }
        return null;
    }


    public JSONObject readInputStream(HttpURLConnection connection) {

        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {


            StringBuilder response = new StringBuilder();
            String responseLine = null;


            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            //Converting stringbuffer to JSON object
            return new JSONObject(response.toString());

        } catch (IOException e) {
            e.printStackTrace();

        }
        return null;
    }


    public URL createURL(String resource) {
        String baseAddress = "http://localhost:8080/rest/service";
        try {
            URL url = new URL(baseAddress.concat(resource));
            return url;
        } catch (MalformedURLException e) {
            e.getMessage();
        }
        return null;
    }

    public boolean decodeMessage(JSONObject msg) {
        String code = msg.getString("code");
        boolean answer = false;
        switch (Integer.parseInt(code)) {
            case 1:
                System.out.println("Success, positiv respons from API");
                answer = true;
                break;
            case 0:
                System.out.println("API failed to execute requestt");
                answer = false;
                break;
            case -2:
                System.out.println("Could not connect to database");
                answer = false;
                break;

        }
        return answer;
    }


    public void sendmessagesToAPI(HttpURLConnection connection, String JSONstring) {

        //Sending JSONOBject
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = JSONstring.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

