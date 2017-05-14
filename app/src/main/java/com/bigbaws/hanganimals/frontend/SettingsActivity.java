package com.bigbaws.hanganimals.frontend;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.frontend.login.LoginActivity;

/**
 * Created by Silas on 9-May-17.
 */

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    private Button changeAnimal_btn, logout_btn, profile_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        profile_btn = (Button) findViewById(R.id.settings_btn_profile);
        changeAnimal_btn = (Button) findViewById(R.id.settings_btn_changeanimal);
        logout_btn = (Button) findViewById(R.id.settings_btn_logout);


        changeAnimal_btn.setOnClickListener(this);
        logout_btn.setOnClickListener(this);
        profile_btn.setOnClickListener(this);





    }

    @Override
    public void onClick(View v) {
        if ( v == changeAnimal_btn) {
            Intent intent = new Intent(SettingsActivity.this, ChangeAnimalActivity.class);
            startActivity(intent);
        }else if (v == logout_btn) {

            Intent intent = new Intent(SettingsActivity.this, LoginActivity.class);
            /*Removes every acitivity in the stack */
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);

        }else if(v == profile_btn) {
            Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}
