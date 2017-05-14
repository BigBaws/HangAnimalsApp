package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.user.User;
import com.bigbaws.hanganimals.frontend.login.LoginActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {


    /* Log */
    private static final String TAG = "";

    SharedPreferences shared;

    public TextView profile_study, profile_name, profile_email, profile_animal, profile_games, profile_won, profile_winstreak, profile_highscore, profile_combo;
    public Button reset;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.profile_activity);


        /* Shared Preferences */
        shared = getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");

        shared = getSharedPreferences("NumberOfGames", Context.MODE_PRIVATE);
        int games = shared.getInt("NumberOfGames", 0);

        shared = getSharedPreferences("Won", Context.MODE_PRIVATE);
        int won = shared.getInt("Won", 0);

        shared = getSharedPreferences("WinStreak", Context.MODE_PRIVATE);
        int winstreak = shared.getInt("WinStreak", 0);

        shared = getSharedPreferences("Highscore", Context.MODE_PRIVATE);
        int highscore = shared.getInt("Highscore", 0);

        shared = getSharedPreferences("Combo", Context.MODE_PRIVATE);
        int combo = shared.getInt("Combo", 0);

        /* Profile Layout and Data */
        profile_study = (TextView) findViewById(R.id.profile_edit_study);
        profile_name = (TextView) findViewById(R.id.profile_edit_name);
        profile_email = (TextView) findViewById(R.id.profile_edit_email);
        profile_animal = (TextView) findViewById(R.id.profile_animal_name);



        profile_study.setText(User.study);
        profile_name.setText(User.name);
        profile_email.setText(User.id);


        profile_animal.setText(animal);
        profile_games = (TextView) findViewById(R.id.profile_games);
        profile_won = (TextView) findViewById(R.id.profile_won);
        profile_winstreak = (TextView) findViewById(R.id.profile_winstreak);
        profile_highscore = (TextView) findViewById(R.id.profile_highscore);
        profile_combo = (TextView) findViewById(R.id.profile_combo);

        profile_games.setText(""+games);
        profile_won.setText(""+won);
        profile_winstreak.setText(""+winstreak);
        profile_highscore.setText(""+highscore);
        profile_combo.setText(""+combo);

        /* Reset Button */
        reset = (Button) findViewById(R.id.profile_btn_reset);
        reset.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (reset.isPressed()) {
            shared = getSharedPreferences("NumberOfGames", Context.MODE_PRIVATE);
            SharedPreferences.Editor egames = shared.edit();
            egames.putInt("NumberOfGames", 0);
            egames.commit();

            shared = getSharedPreferences("Won", Context.MODE_PRIVATE);
            SharedPreferences.Editor ewon = shared.edit();
            ewon.putInt("Won", 0);
            ewon.commit();

            shared = getSharedPreferences("WinStreak", Context.MODE_PRIVATE);
            SharedPreferences.Editor ewin = shared.edit();
            ewin.putInt("WinStreak", 0);
            ewin.commit();

            shared = getSharedPreferences("Highscore", Context.MODE_PRIVATE);
            SharedPreferences.Editor ehigh = shared.edit();
            ehigh.putInt("Highscore", 0);
            ehigh.commit();

            shared = getSharedPreferences("Combo", Context.MODE_PRIVATE);
            SharedPreferences.Editor ecomb = shared.edit();
            ecomb.putInt("Combo", 0);
            ecomb.commit();

            /* Layout */
            profile_games.setText("0");
            profile_won.setText("0");
            profile_winstreak.setText("0");
            profile_highscore.setText("0");
            profile_combo.setText("0");

            Toast.makeText(ProfileActivity.this, "Highscore was reset.", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(ProfileActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }
    }
}