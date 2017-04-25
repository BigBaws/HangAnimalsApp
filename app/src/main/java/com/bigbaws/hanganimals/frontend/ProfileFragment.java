package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
public class ProfileFragment extends Fragment implements View.OnClickListener {

    /* Firebase */
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    /* Log */
    private static final String TAG = "";

    SharedPreferences shared;

    public TextView profile_name, profile_email, profile_animal, profile_games, profile_won, profile_winstreak, profile_highscore;
    public Button logout, reset;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.profile_fragment, container, false);

        /* Get Firebase Instance */
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        /* Shared Preferences */
        shared = this.getActivity().getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");

        shared = this.getActivity().getSharedPreferences("NumberOfGames", Context.MODE_PRIVATE);
        int games = shared.getInt("NumberOfGames", 0);

        shared = this.getActivity().getSharedPreferences("Won", Context.MODE_PRIVATE);
        int won = shared.getInt("Won", 0);

        shared = this.getActivity().getSharedPreferences("WinStreak", Context.MODE_PRIVATE);
        int winstreak = shared.getInt("WinStreak", 0);

        shared = this.getActivity().getSharedPreferences("Highscore", Context.MODE_PRIVATE);
        int highscore = shared.getInt("Highscore", 0);

        /* Profile Layout and Data */
        profile_name = (TextView) v.findViewById(R.id.profile_edit_name);
        profile_email = (TextView) v.findViewById(R.id.profile_edit_email);
        profile_animal = (TextView) v.findViewById(R.id.profile_animal_name);
        logout = (Button) v.findViewById(R.id.profile_btn_logout);
        logout.setOnClickListener(this);

        /* Get the user */
        DatabaseReference user = database.getReference("v0/users/" + mAuth.getCurrentUser().getUid());
        user.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User currentUser = dataSnapshot.getValue(User.class);
                currentUser.setId(dataSnapshot.getKey());
                profile_name.setText(currentUser.name);
                profile_email.setText(currentUser.email);

                System.out.println("************* Profile **************");
                System.out.println(currentUser.id);
                System.out.println(currentUser.name);
                System.out.println(currentUser.email);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getActivity(), "Database Error.", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Failed");
            }
        });

        profile_animal.setText(animal);
        profile_games  = (TextView) v.findViewById(R.id.profile_games);
        profile_won  = (TextView) v.findViewById(R.id.profile_won);
        profile_winstreak = (TextView) v.findViewById(R.id.profile_winstreak);
        profile_highscore = (TextView) v.findViewById(R.id.profile_highscore);

        profile_games.setText(""+games);
        profile_won.setText(""+won);
        profile_winstreak.setText(""+winstreak);
        profile_highscore.setText(""+highscore);

        /* Reset Button */
        reset = (Button) v.findViewById(R.id.profile_btn_reset);
        reset.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View view) {
        if (logout.isPressed()) {
            mAuth.signOut();
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else if (reset.isPressed()) {
            shared = this.getActivity().getSharedPreferences("NumberOfGames", Context.MODE_PRIVATE);
            SharedPreferences.Editor egames = shared.edit();
            egames.putInt("NumberOfGames", 0);
            egames.commit();

            shared = this.getActivity().getSharedPreferences("Won", Context.MODE_PRIVATE);
            SharedPreferences.Editor ewon = shared.edit();
            ewon.putInt("Won", 0);
            ewon.commit();

            shared = this.getActivity().getSharedPreferences("WinStreak", Context.MODE_PRIVATE);
            SharedPreferences.Editor ewin = shared.edit();
            ewin.putInt("WinStreak", 0);
            ewin.commit();

            shared = this.getActivity().getSharedPreferences("Highscore", Context.MODE_PRIVATE);
            SharedPreferences.Editor ehigh = shared.edit();
            ehigh.putInt("Highscore", 0);
            ehigh.commit();

            /* Layout */
            profile_games.setText("0");
            profile_won.setText("0");
            profile_winstreak.setText("0");
            profile_highscore.setText("0");

            Toast.makeText(getActivity(), "Highscore was reset.", Toast.LENGTH_SHORT).show();

            Fragment menu = new MainMenuFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragment_body, menu)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }
}
