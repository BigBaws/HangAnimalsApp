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



    }


    /* Click Listeners */
    @Override
    public void onClick(View view) {
        if (btn_play.isPressed()) {
            // token, userid, gameid, endpath
            new SinglePlayerAsync().execute(User.token, User.id, "0", "singleplayer/create");

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



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e("TEST", "Activity result is working");

        if (requestCode == PayPalController.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm =
                        data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if (confirm != null) {
                    try {
                        Log.i(PayPalController.TAG, confirm.toJSONObject().toString(4));
                        Log.i(PayPalController.TAG, confirm.getPayment().toJSONObject().toString(4));
                        /**
                         *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                         * or consent completion.
                         * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                         * for more details.
                         *
                         * For sample mobile backend interactions, see
                         * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                         */
                        Toast.makeText(this, "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG).show();
//                        displayResultText("PaymentConfirmation info received from PayPal");


                    } catch (JSONException e) {
                        Log.e(PayPalController.TAG, "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i(PayPalController.TAG, "The user canceled.");
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        PayPalController.TAG,
                        "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
            }
        } else if (requestCode == PayPalController.REQUEST_CODE_FUTURE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("FuturePaymentExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("FuturePaymentExample", authorization_code);

//                        sendAuthorizationToServer(auth);
//                        displayResultText("Future Payment code received from PayPal");

                    } catch (JSONException e) {
                        Log.e("FuturePaymentExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("FuturePaymentExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "FuturePaymentExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        } else if (requestCode == PayPalController.REQUEST_CODE_PROFILE_SHARING) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization auth =
                        data.getParcelableExtra(PayPalProfileSharingActivity.EXTRA_RESULT_AUTHORIZATION);
                if (auth != null) {
                    try {
                        Log.i("ProfileSharingExample", auth.toJSONObject().toString(4));

                        String authorization_code = auth.getAuthorizationCode();
                        Log.i("ProfileSharingExample", authorization_code);

//                        sendAuthorizationToServer(auth);
//                        displayResultText("Profile Sharing code received from PayPal");

                    } catch (JSONException e) {
                        Log.e("ProfileSharingExample", "an extremely unlikely failure occurred: ", e);
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Log.i("ProfileSharingExample", "The user canceled.");
            } else if (resultCode == PayPalFuturePaymentActivity.RESULT_EXTRAS_INVALID) {
                Log.i(
                        "ProfileSharingExample",
                        "Probably the attempt to previously start the PayPalService had an invalid PayPalConfiguration. Please see the docs.");
            }
        }
    }
}