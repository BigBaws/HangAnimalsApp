package com.bigbaws.hanganimals.frontend;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.util.CustomListAdapter;
import com.bigbaws.hanganimals.backend.util.PayPalController;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalProfileSharingActivity;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class ChangeAnimalActivity extends Activity implements View.OnClickListener {


    private String itemToBuy;
    private String buyPrice;
    private ImageButton paypal_button;
    private ImageView image_animal;
    private Button change_animal_save;
    private ListView animalsListView;
    String[] animals = {"White-sheep", "Pink-sheep", "Black-sheep", "Blue-bunny", "Green-dragon"};
    Integer[] imgId = {R.drawable.sheep_white, R.drawable.sheep_pink,  R.drawable.sheep_black, R.drawable.bunny_blue, R.drawable.dragon_green};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.change_animal_activity_new);

        /* Shared Preferences */
        SharedPreferences shared = ChangeAnimalActivity.this.getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");

        /* Below variables is just to prevent app from crashing while testing */
        itemToBuy = animal;
        buyPrice = "5000";

        /* Layout */
        image_animal = (ImageView) findViewById(R.id.change_animal_image);
        change_animal_save = (Button) findViewById(R.id.change_animal_btn_save);
        change_animal_save.setOnClickListener(this);
        paypal_button = (ImageButton) findViewById(R.id.paypal_button);

        if(animal.equalsIgnoreCase("SheepWhite")) {
            image_animal.setImageResource(R.drawable.sheep_white);
        }else if(animal.equalsIgnoreCase("SheepPink")) {
            image_animal.setImageResource(R.drawable.sheep_pink);
        }else if(animal.equalsIgnoreCase("BunnyBlue")) {
            image_animal.setImageResource(R.drawable.bunny_blue);
        }else if(animal.equalsIgnoreCase("DragonGreen")) {
            image_animal.setImageResource(R.drawable.dragon_green);
        }else if(animal.equalsIgnoreCase("SheepBlack")) {
            image_animal.setImageResource(R.drawable.sheep_black);
        }


        paypal_button.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        paypal_button.setImageResource(R.drawable.paypal_button_pressed);
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        paypal_button.setImageResource(R.drawable.paypal_button);

                        PayPalPayment thingToBuy = setThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE, itemToBuy, buyPrice);
                        Intent intent = new Intent(ChangeAnimalActivity.this, PaymentActivity.class);
                        // send the same configuration for restart resiliency
                        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, PayPalController.config);
                        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, thingToBuy);
                        startActivityForResult(intent, PayPalController.REQUEST_CODE_PAYMENT);


                    }

                }

                return true;
            }

        });




        CustomListAdapter adapter = new CustomListAdapter(this, animals, imgId);

        animalsListView = (ListView) findViewById(android.R.id.list);
        animalsListView.setAdapter(adapter);
        animalsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 /* Shared Preferences */
                SharedPreferences shared = getSharedPreferences("TempAnimal", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();

                /* Get ListView String */
                String selectedItem = animals[+position];

                if (selectedItem == "White-sheep") {
                    itemToBuy = "White Sheep";
                    buyPrice = "5000";
                    image_animal.setImageResource(R.drawable.sheep_white);
                    editor.putString("TempAnimal", "SheepWhite");
                    editor.commit();
                } else if (selectedItem == "Pink-sheep") {
                    itemToBuy = "Pink Sheep";
                    buyPrice = "6000";
                    image_animal.setImageResource(R.drawable.sheep_pink);
                    editor.putString("TempAnimal", "SheepPink");
                    editor.commit();
                } else if (selectedItem == "Blue-bunny") {
                    itemToBuy = "Blue Bunny";
                    buyPrice = "7000";
                    image_animal.setImageResource(R.drawable.bunny_blue);
                    editor.putString("TempAnimal", "BunnyBlue");
                    editor.commit();
                } else if (selectedItem == "Green-dragon") {
                    itemToBuy = "Green Dragon";
                    buyPrice = "9999";
                    image_animal.setImageResource(R.drawable.dragon_green);
                    editor.putString("TempAnimal", "DragonGreen");
                    editor.commit();
                }  else if (selectedItem == "Black-sheep") {
                    itemToBuy = "Black Sheep";
                    buyPrice = "8000";
                    image_animal.setImageResource(R.drawable.sheep_black);
                    editor.putString("TempAnimal", "SheepBlack");
                    editor.commit();
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        if (change_animal_save.isPressed()) {
            /* Shared Preferences */
            SharedPreferences temp = getSharedPreferences("TempAnimal", Context.MODE_PRIVATE);
            String tempanimal = temp.getString("TempAnimal", "");

            if (!tempanimal.isEmpty()) {
                SharedPreferences shared = getSharedPreferences("Animal", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("Animal", tempanimal);
                editor.commit();
            }
            Intent intent = new Intent(ChangeAnimalActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }
    }


    private PayPalPayment setThingToBuy(String paymentIntent, String name, String price) {
        return new PayPalPayment(new BigDecimal(price), "DKK", name,
                paymentIntent);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode != RESULT_CANCELED) {
            if (requestCode == PayPalController.REQUEST_CODE_PAYMENT) {
                if (resultCode == Activity.RESULT_OK) {
                    PaymentConfirmation response=
                            data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                    if (response != null) {
                        try {
                            Log.i(PayPalController.TAG, response.toJSONObject().toString(4));
                            Log.i(PayPalController.TAG, response.getPayment().toJSONObject().toString(4));


                            /**
                             *  TODO: send 'confirm' (and possibly confirm.getPayment() to your server for verification
                             * or consent completion.
                             * See https://developer.paypal.com/webapps/developer/docs/integration/mobile/verify-mobile-payment/
                             * for more details.
                             *
                             * For sample mobile backend interactions, see
                             * https://github.com/paypal/rest-api-sdk-python/tree/master/samples/mobile_backend
                             */


                            if(response.toJSONObject().getJSONObject("response").getString("state").equalsIgnoreCase("approved")) {
                                System.out.println("Purchase is " + response.toJSONObject().getJSONObject("response").getString("state"));
                            }else {
                                System.out.println("Purchase is " + response.toJSONObject().getJSONObject("response").getString("state"));
                            }

                            System.out.println("LOLOOLOLO " + response.getPayment() );

                            Toast.makeText(this, "PaymentConfirmation info received from PayPal", Toast.LENGTH_LONG).show();


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
            }

        }else {
            Toast.makeText(this, "Purchase cancelled", Toast.LENGTH_LONG).show();
        }
    }
}

