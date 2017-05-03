package com.bigbaws.hanganimals.backend.asynctasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.bigbaws.hanganimals.backend.dao.RESTConnector;
import com.bigbaws.hanganimals.backend.exceptions.DAOException;

import org.json.JSONObject;

/**
 * Created by Silas on 03-05-2017.
 */

public class SinglePlayerAsync extends AsyncTask<String, Void, JSONObject> {


    @Override
    protected JSONObject doInBackground(String... params) {



        try {

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("token", params[0])
                    .appendQueryParameter("userid", params[1])
                    .appendQueryParameter("gameid", params[2]);
            String encodedParams = builder.build().getEncodedQuery();
            Log.e("putParams", encodedParams);


            return RESTConnector.PUTQuery(encodedParams, params[3]);


        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    protected void onPostExecute(JSONObject jsonObject) {


        if(jsonObject == null) {
            Log.e("ERROR singplayerAsync:", "Post result is null");
        }else {
//            Log.e("Create JSON object", jsonObject.toString());
            System.out.println("SINGLEPLAYERASYNC " + jsonObject);
        }
    }
}
