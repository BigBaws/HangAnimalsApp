package com.bigbaws.hanganimals.frontend;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.user.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by BigBaws on 11-Jan-17.
 */

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener  {

    /* Firebase */
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    /* Log */
    private static final String TAG = "";

    /* Welcome Text */
    private TextView welcome;

    /* Buttons */
    private Button btn_play, btn_multiplayer, btn_change, btn_profile;

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

        /* Get Firebase Instance */
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        /* Welcome & Buttons w. ClickListeners */
        welcome = (TextView) findViewById(R.id.mainmenu_welcome);
        btn_play = (Button) findViewById(R.id.mainmenu_btn_play);
        btn_play.setOnClickListener(this);
        btn_multiplayer = (Button) findViewById(R.id.mainmenu_btn_challenge);
        btn_multiplayer.setOnClickListener(this);
        btn_change = (Button) findViewById(R.id.mainmenu_btn_changeanimal);
        btn_change.setOnClickListener(this);
        btn_profile = (Button) findViewById(R.id.mainmenu_btn_profile);
        btn_profile.setOnClickListener(this);

        /* Get the user */
        DatabaseReference user = database.getReference("v0/users/" + mAuth.getCurrentUser().getUid());
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                currentUser.setId(dataSnapshot.getKey());
                welcome.setText("Hello "+currentUser.name);

                System.out.println("************* User **************");
                System.out.println(currentUser.id);
                System.out.println(currentUser.name);
                System.out.println(currentUser.email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(MainMenuActivity.this, "Database Error.", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Failed");
            }
        });

    }


    /* Click Listeners */
    @Override
    public void onClick(View view) {
        if (btn_play.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, PlayActivity.class);
            startActivity(intent);
        } else if (btn_multiplayer.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, MultiplayerActivity.class);
            startActivity(intent);
        } else if (btn_change.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, ChangeAnimalActivity.class);
            startActivity(intent);
        } else if (btn_profile.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }
}