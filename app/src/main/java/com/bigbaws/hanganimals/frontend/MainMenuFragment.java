package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class MainMenuFragment extends Fragment implements View.OnClickListener  {
    /* View & Fragments */
    private View mView;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;

    /* Firebase */
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    /* Log */
    private static final String TAG = "";

    /* Welcome Text */
    private TextView welcome;

    /* Buttons */
    private Button btn_play, btn_challenge, btn_change, btn_profile;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        mView = i.inflate(R.layout.mainmenu_fragment, container, false);

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
        welcome = (TextView) mView.findViewById(R.id.mainmenu_welcome);
        btn_play = (Button) mView.findViewById(R.id.mainmenu_btn_play);
        btn_play.setOnClickListener(this);
        btn_change = (Button) mView.findViewById(R.id.mainmenu_btn_changeanimal);
        btn_change.setOnClickListener(this);
        btn_challenge = (Button) mView.findViewById(R.id.mainmenu_btn_challenge);
        btn_challenge.setOnClickListener(this);
        btn_profile = (Button) mView.findViewById(R.id.mainmenu_btn_profile);
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
                Toast.makeText(getActivity(), "Database Error.", Toast.LENGTH_SHORT).show();
                Log.w(TAG, "Failed");
            }
        });

        return mView;
    }


    /* Click Listeners */
    @Override
    public void onClick(View view) {
        mFragmentManager = getFragmentManager();
        if (btn_play.isPressed()) {
            FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
            Bundle b = new Bundle();
            b.putString("newsid", mAuth.getCurrentUser().getDisplayName());
            PlayFragment pf = new PlayFragment();
            pf.setArguments(b);
            fragmentTransaction.replace(R.id.fragment_body, pf).addToBackStack(null).commit();
        } else if (btn_challenge.isPressed()) {
            Intent intent = new Intent(getActivity(), MultiplayerActivity.class);
            startActivity(intent);
        } else if (btn_change.isPressed()) {
            Fragment change = new ChangeAnimalFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragment_body, change)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        } else if (btn_profile.isPressed()) {
            Fragment profile = new ProfileFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragment_body, profile)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }
}