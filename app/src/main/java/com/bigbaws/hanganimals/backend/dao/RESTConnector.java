package com.bigbaws.hanganimals.backend.dao;


import android.util.Log;

import com.bigbaws.hanganimals.backend.exceptions.DAOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLOutput;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Silas on 01-May-17.
 */

public class RESTConnector {

    static final String baseURL = "http://ubuntu4.javabog.dk:4176/HangAnimalsREST/webresources";
    public static String GETResponseString;

    /* HTTP POST REQUEST METHOD */
    public static JSONObject POSTQuery(String encodedParams, String endPath) {
        try {
            URL url = new URL(baseURL + endPath);
            Log.e("POST URL", url.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(encodedParams);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("POST QUERY HTTP RESPONSE CODE = " + responseCode);

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                Log.e("POST QUERY", "HTTP OK");

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while((line = in.readLine()) != null) {
                    sb.append(line);
                    break;
                }

                in.close();

                    JSONObject JsonObject = new JSONObject(sb.toString());
                    return JsonObject;
            }
            else {

                System.out.println("POST QUERY HTTP RESPONSE CODE = "+responseCode);
                return null;
            }
        } catch(Exception e){
            Log.e("Exception:", e.getMessage());
            e.printStackTrace();

        }
        return null;
    }


    /* HTTP GET REQUEST METHOD */
    public static JSONObject GETQuery(String requestURL) throws DAOException {

        try {
            URL urls = new URL(baseURL + requestURL);
            StringBuffer data = new StringBuffer(1048);

            try (BufferedReader buffer = new BufferedReader(new InputStreamReader(urls.openStream(), "UTF-8"))) {

                String tmp = "";

                while ((tmp = buffer.readLine()) != null)
                    data.append(tmp).append("\n");


                System.out.println("DATA FROM GETQuery : " + data.toString());
                GETResponseString = data.toString();

                JSONObject jsonObject = new JSONObject(data.toString());

                return jsonObject;


            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            throw new DAOException("GET QUERY: Error connecting to the host " + requestURL);
        }

        return null;
    }


    /* HTTP PUT REQUEST METHOD */
    public static JSONObject PUTQuery (String encodedParams, String endPath) throws DAOException {

        try {

            Log.e("PUT Params",encodedParams );
            URL url = new URL(baseURL + endPath);
            System.out.println("PUTQuery PATH = " + url.toString());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("PUT");
            conn.setDoInput(true);
            conn.setDoOutput(true);


            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(encodedParams);
            writer.flush();
            writer.close();
            os.close();

            conn.connect();

            int responseCode = conn.getResponseCode();
            System.out.println("PUT QUERY HTTP RESPONSE CODE = " + responseCode);
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }

                in.close();

                JSONObject JsonObject = new JSONObject(sb.toString());

                return JsonObject;

            } else {

                System.out.println("PUT QUERY HTTP RESPONSE CODE = " + responseCode);
                return null;
            }
        } catch (Exception e) {
            Log.e("Exception:", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}
