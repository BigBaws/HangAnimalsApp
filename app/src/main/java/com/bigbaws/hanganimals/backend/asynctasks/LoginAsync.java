package com.bigbaws.hanganimals.backend.asynctasks;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.bigbaws.hanganimals.backend.dao.RESTConnector;
import com.bigbaws.hanganimals.backend.user.User;
import com.bigbaws.hanganimals.frontend.MainMenuActivity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Silas on 25-04-2017.
 */

public class LoginAsync extends AsyncTask<String, Void, JSONObject> {

    private ProgressDialog progressDialog;
    Context context;

    public LoginAsync(Context context) {
        this.context = context;
    }



    @Override
    protected JSONObject doInBackground(String... params) {

        /* Encode username and password */
        try {

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", params[0])
                    .appendQueryParameter("password", params[1]);
            String encodedParams = builder.build().getEncodedQuery();

            return RESTConnector.POSTQuery(encodedParams, params[2]);


        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(context,
                "Please wait",
                "Trying to sign in.");
    }


    /* Set user details */
    @Override
    protected void onPostExecute(JSONObject result) {
        progressDialog.dismiss();
        System.out.println("POST result: " + result);


        if (result == null) {
            Toast.makeText(context, "Wrong Student ID or Password!", Toast.LENGTH_LONG).show();
            System.out.println("onPostExecute result is null");
        }
        else {

            try {
                User user  = new User(result.getString("userid"), result.getString("name"), result.getString("study"), result.getString("token"), result.getString("singleplayer"), result.getString("multiplayer"));
                Log.e("USER ID", User.id);
                Log.e("USER TOKEN", User.token);
                Log.e("USER SINGLEPLAY ID", User.singleplayer);
                Log.e("USER Multi Play ID", User.multiplayer);
                Intent mainIntent = new Intent(context, MainMenuActivity.class);
                context.startActivity(mainIntent);

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }




}
