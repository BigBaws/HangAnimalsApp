package com.bigbaws.hanganimals.frontend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;

public class Splash extends Activity {

    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 3500;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        ImageView image = (ImageView)findViewById(R.id.splashscreen);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.rotation);
        image.startAnimation(animation);

        TextView text = (TextView) findViewById(R.id.splash_HangAnimals);
        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade);
        text.startAnimation(animation1);

        /* New Handler to start the Menu-Activity 
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this, MainMenuActivity.class);
                startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}