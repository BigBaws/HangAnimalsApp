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
    Context c;

    public LoginAsync(Context c) {
        this.c = c;
    }

    @Override
    protected JSONObject doInBackground(String... params) {


        try {

            Uri.Builder builder = new Uri.Builder()
                    .appendQueryParameter("username", params[0])
                    .appendQueryParameter("password", params[1]);
            String encodedParams = builder.build().getEncodedQuery();
            Log.e("postParams", encodedParams);

            return RESTConnector.POSTQuery(encodedParams, params[2]);


        }catch (Exception e) {
            e.printStackTrace();
        }





        return null;
    }




    @Override
    protected void onPostExecute(JSONObject result) {
        progressDialog.dismiss();
        System.out.println("POST result: " + result);


        if (result == null) {
            Toast.makeText(c, "Wrong Student ID or Password!", Toast.LENGTH_LONG).show();
            System.out.println("onPostExecute result is null");
        }
        else {
//            Toast.makeText(c, "result received from background", Toast.LENGTH_LONG).show();

            try {

                User user  = new User(result.getString("userid"), result.getString("name"), result.getString("study"), result.getString("token"));
                Log.e("USER", User.id);
                Intent mainIntent = new Intent(c, MainMenuActivity.class);
//                mainIntent.putExtra("ID", result.getString("userid"));
                c.startActivity(mainIntent);

                System.out.println("POST; user token: " + User.token);


            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }

    @Override
    protected void onPreExecute() {
        progressDialog = ProgressDialog.show(c,
                "Please wait",
                "Trying to sign in.");
    }



}
