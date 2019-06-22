package com.company.api.net;


import com.company.api.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class SSLConnection {
    private String urlConnection;

    public SSLConnection(String urlConnection) {
        this.urlConnection = urlConnection;
    }

    public JSONObject getSSLConnection() throws IOException {
        URL url = new URL(this.urlConnection);
        HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();

        return getAndConvertResponseToJson(conn);
    }

    private JSONObject getAndConvertResponseToJson(HttpsURLConnection connection) throws IOException {
        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //convert to com.company.json
        JSONObject jsonObject = new JSONObject(response.toString());


        return jsonObject;
    }

    private void printContent(HttpsURLConnection con) {
        if (con != null) {

            try {

                System.out.println("****** Content of the URL ********");
                BufferedReader br =
                        new BufferedReader(
                                new InputStreamReader(con.getInputStream()));

                String input;

                while ((input = br.readLine()) != null) {
                    System.out.println(input);
                }
                br.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public String getUrlConnection() {
        return urlConnection;
    }

    public void setUrlConnection(String urlConnection) {
        this.urlConnection = urlConnection;
    }
}
