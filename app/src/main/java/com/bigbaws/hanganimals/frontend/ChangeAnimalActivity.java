package com.bigbaws.hanganimals.frontend;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
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

import java.math.BigDecimal;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class ChangeAnimalActivity extends ListActivity implements View.OnClickListener {


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

                        PayPalPayment thingToBuy = getThingToBuy(PayPalPayment.PAYMENT_INTENT_SALE);

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
                    image_animal.setImageResource(R.drawable.sheep_white);
                    editor.putString("TempAnimal", "SheepWhite");
                    editor.commit();
                } else if (selectedItem == "Pink-sheep") {
                    image_animal.setImageResource(R.drawable.sheep_pink);
                    editor.putString("TempAnimal", "SheepPink");
                    editor.commit();
                } else if (selectedItem == "Blue-bunny") {
                    image_animal.setImageResource(R.drawable.bunny_blue);
                    editor.putString("TempAnimal", "BunnyBlue");
                    editor.commit();
                } else if (selectedItem == "Green-dragon") {
                    image_animal.setImageResource(R.drawable.dragon_green);
                    editor.putString("TempAnimal", "DragonGreen");
                    editor.commit();
                }  else if (selectedItem == "Black-sheep") {
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


    private PayPalPayment getThingToBuy(String paymentIntent) {
        return new PayPalPayment(new BigDecimal("12999.95"), "DKK", "White Bunny",
                paymentIntent);
    }



}
