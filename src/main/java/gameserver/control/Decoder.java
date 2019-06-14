package gameserver.control;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class Decoder {

    BufferedReader in = null;

        public HttpURLConnection createConnection(URL urlForPOSTRequest){
            try {
                HttpURLConnection connection = (HttpURLConnection) urlForPOSTRequest.openConnection();

                connection.setRequestMethod("POST");
                connection.setRequestProperty("Content-Type", "application/json; utf-8");
                connection.setRequestProperty("Accept", "application/json");

                //Enable writing to outputStream
                connection.setDoOutput(true);
                return connection;
            }catch (ProtocolException x){
                x.getMessage();
                }catch (IOException e){
                    e.getMessage();
                }


            return null;
        }

        public JSONObject readInputStream(HttpURLConnection connection){
            try {

                String readLine = null;
                in = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer response = new StringBuffer();

                while ((readLine = in.readLine()) != null) {

                    response.append(readLine);

                }
                in.close();

                //Converting stringbuffer to JSON object
                JSONObject obj = new JSONObject(response.toString());

                return obj;
            }catch (IOException e){
                e.getMessage();

            }finally {
                try {

                    in.close();

                }catch (IOException e){
                    e.getMessage();
                }
            }
            return null;
        }

    }


