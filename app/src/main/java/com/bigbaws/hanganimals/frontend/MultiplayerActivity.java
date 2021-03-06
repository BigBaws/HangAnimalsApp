package com.bigbaws.hanganimals.frontend;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.dao.RESTConnector;
import com.bigbaws.hanganimals.backend.exceptions.DAOException;
import com.bigbaws.hanganimals.backend.user.User;
import com.bigbaws.hanganimals.backend.util.GameRoomsCustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Silas on 11-May-17.
 */

public class MultiplayerActivity extends AppCompatActivity {

    private Button createRoomBtn;
    private ListView roomsListView;
    private ArrayList<String> roomsList = new ArrayList<String>();
    private ProgressDialog progressDialog;
    private final Handler handler = new Handler();
    private GameRoomsCustomAdapter adapter;
    private boolean activityIsActive = true;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.multiplayer_activity);

        /* Initialise game rooms list */
        adapter = new GameRoomsCustomAdapter(roomsList, MultiplayerActivity.this);
        roomsListView = (ListView) findViewById(R.id.currentGameRooms);
        roomsListView.setAdapter(adapter);

        createRoomBtn = (Button) findViewById(R.id.multiplayer_btn_createRoom);
        createRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("HEY", "create room button clicked ");
                createGameAsync();

            }
        });

    }
    /* Update game rooms list every 5 seconds */
    public void updateList() {
        handler.postDelayed(new Runnable()   {
            @Override
            public void run() {

                if(activityIsActive) {
                    Log.e("Update", "Room List Updated");
                    addRoomsToListAsync();
                    handler.postDelayed(this, 5000);
                }else {
                    handler.removeCallbacks(this);
                }
            }
        }, 0);
    }
    /* Async call add rooms to list */
    public void addRoomsToListAsync() {
        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {
                    return RESTConnector.GETQuery("/multiplayer/room/listRooms?token=" + params[0] + "&userid=" + params[0]);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                if(jsonObject == null) {
                    Log.e("addRoomsToList", "Object is null");
                }else {

                    try {
                        JSONArray array = jsonObject.getJSONArray("games");
                        System.out.println("GAMEROOMS LIST: " + array);

                        roomsList.clear();

                        for (int i = 0; i < array.length(); i++) {

                            String roomid = array.getJSONObject(i).getString("roomid");
                            roomsList.add(roomid);
                        }

                        adapter.notifyDataSetChanged();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }

        }.execute(User.token, User.id);
    }

    /* Async call create game room */
    public void createGameAsync() {
        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {


                try {

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("token", params[0]);
                    String encodedParams = builder.build().getEncodedQuery();

                    return RESTConnector.PUTQuery(encodedParams, params[1]);


                }catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(MultiplayerActivity.this,
                        "Please wait",
                        "Creating New Game");

            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                progressDialog.dismiss();
                recreate();

                Toast.makeText(MultiplayerActivity.this, "New room created!", Toast.LENGTH_SHORT).show();
                System.out.println("CREATE GAME OBJECT: " + jsonObject);
            }

        }.execute(User.token, "/multiplayer/room/create");
    }


    @Override
    protected void onResume() {
        super.onResume();
        Log.e("TEst", "OnResume");
        activityIsActive = true;
        /* Makes a call every 5 seconds to update list of active players in a room */
        updateList();
    }

    @Override
    protected void onPause() {
        super.onPause();
        activityIsActive = false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}