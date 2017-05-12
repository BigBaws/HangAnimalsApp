package com.bigbaws.hanganimals.frontend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.util.MultiplayerCustomAdapter;

import java.util.ArrayList;

/**
 * Created by BigBaws on 11-Jan-17.
 */

public class MultiplayerActivity extends AppCompatActivity {

    private ListView roomsListView;
    private ArrayList<String> roomsList = new ArrayList<String>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.multiplayer_activity);

        roomsList.add("Room 1");
        roomsList.add("Room 2");
        roomsList.add("Room 3");
        roomsList.add("Room 4");
        roomsList.add("Room 5");
        roomsList.add("Room 6");
        roomsList.add("Room 7");
        roomsList.add("Room 8");
        roomsList.add("Room 9");
        roomsList.add("Room 10");


        MultiplayerCustomAdapter adapter = new MultiplayerCustomAdapter(roomsList, this);
        roomsListView = (ListView) findViewById(R.id.currentGameRooms);
        roomsListView.setAdapter(adapter);


    }

}