package com.bigbaws.hanganimals.frontend;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.dao.RESTConnector;
import com.bigbaws.hanganimals.backend.user.User;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Silas on 8-May-17.
 */

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener  {

    private ProgressDialog progressDialog;

    /* Log */
    private static final String TAG = "";

    /* Welcome Text */
    private TextView welcome;

    /* Buttons */
    private Button btn_play, btn_multiplayer, btn_settings, btn_chat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.mainmenu_activity);

        /* Check Bundle */
        if (savedInstanceState != null) {
            System.out.println("Bundle " + savedInstanceState);
        } else {
            System.out.println("Bundle was null");
        }

        /* Welcome & Buttons w. ClickListeners */
        welcome = (TextView) findViewById(R.id.mainmenu_welcome);
        btn_play = (Button) findViewById(R.id.mainmenu_btn_play);
        btn_play.setOnClickListener(this);
        btn_multiplayer = (Button) findViewById(R.id.mainmenu_btn_challenge);
        btn_multiplayer.setOnClickListener(this);
        btn_settings = (Button) findViewById(R.id.mainmenu_btn_settings);
        btn_settings.setOnClickListener(this);
        btn_chat = (Button) findViewById(R.id.mainmenu_btn_chat);
        btn_chat.setOnClickListener(this);

    }


    /* Click Listeners */
    @Override
    public void onClick(View view) {
        if (btn_play.isPressed()) {
            createSinglePlayerGameAsync();
        } else if (btn_multiplayer.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, MultiplayerActivity.class);
            startActivity(intent);
        } else if (btn_settings.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
            startActivity(intent);
        } else if (btn_chat.isPressed()) {
            joinChatRoomAsync();
            Intent intent = new Intent(MainMenuActivity.this, ChatActivity.class);
            startActivity(intent);
        }
    }
    /* Create game async call */
    public void createSinglePlayerGameAsync() {
        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {


                try {

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("token", User.token)
                            .appendQueryParameter("userid", User.id)
                            .appendQueryParameter("gameid", User.singleplayer);
                    String encodedParams = builder.build().getEncodedQuery();

                    return RESTConnector.PUTQuery(encodedParams, params[0]);


                }catch (Exception e) {
                    e.printStackTrace();
                }



                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(MainMenuActivity.this,
                        "Please wait",
                        "Creating New Game");

            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                progressDialog.dismiss();
                System.out.println("OBJECT IN SINGLEPLAYER GAME CREATION = " + jsonObject);


                if(jsonObject == null) {
                    Log.e("CREATE GAME ERROR", "Singleplayer");
                } else {

                    try {
                        String hiddenWord = jsonObject.getString("word");
                        Intent intent = new Intent(MainMenuActivity.this, PlayActivity.class);
                        intent.putExtra("word", hiddenWord);
                        startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }






            }


        }.execute("/singleplayer/create");
    }

    /* join game async call */
    public void joinChatRoomAsync() {

        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("token", User.token)
                            .appendQueryParameter("userid", User.id);
                    String encodedParams = builder.build().getEncodedQuery();

                    Log.e("Join Chat room", encodedParams);

                    return RESTConnector.POSTQuery(encodedParams, params[0]);


                }catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                System.out.println("JOIN CHAT ROOM: = " + jsonObject);

            }


        }.execute("/chat/join");
    }
}