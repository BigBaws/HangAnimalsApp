package com.bigbaws.hanganimals.frontend;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.asynctasks.SinglePlayerAsync;
import com.bigbaws.hanganimals.backend.user.User;
import com.bigbaws.hanganimals.backend.util.PayPalController;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;

/**
 * Created by BigBaws on 11-Jan-17.
 */

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener  {

    /* Log */
    private static final String TAG = "";

    /* Welcome Text */
    private TextView welcome;

    /* Buttons */
    private Button btn_play, btn_multiplayer, btn_settings, btn_profile;



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
        btn_profile = (Button) findViewById(R.id.mainmenu_btn_profile);
        btn_profile.setOnClickListener(this);



    }


    /* Click Listeners */
    @Override
    public void onClick(View view) {
        if (btn_play.isPressed()) {
            // token, userid, gameid, endpath
//            new SinglePlayerAsync().execute(User.token, User.id, "0", "/singleplayer/create");

            Intent intent = new Intent(MainMenuActivity.this, PlayActivity.class);
            startActivity(intent);
        } else if (btn_multiplayer.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, MultiplayerActivity.class);
            startActivity(intent);
        } else if (btn_settings.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
            startActivity(intent);

        } else if (btn_profile.isPressed()) {
            Intent intent = new Intent(MainMenuActivity.this, ProfileActivity.class);
            startActivity(intent);
        }
    }

}