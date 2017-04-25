package com.bigbaws.hanganimals.frontend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;

/**
 * Created by BigBaws on 11-Jan-17.
 */

public class MultiplayerActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText secretword;
    private Button begin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.multiplayer_activity);

        secretword = (EditText) findViewById(R.id.multiplayer_secretword);
        begin = (Button) findViewById(R.id.multiplayer_btn_begin);
        begin.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (begin.isPressed()) {
            Intent intent = new Intent(MultiplayerActivity.this, PlayMultiActivity.class);
            intent.putExtra("SecretWord", secretword.getText().toString());
            startActivity(intent);
        }
    }
}