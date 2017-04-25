package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.logic.GameLogic;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class PlayMultiActivity extends AppCompatActivity implements View.OnClickListener {

    GameLogic logic = new GameLogic();

    TextView info, tvhighscore, tvhighscoretxt, tvgamescore, tvgamescoretxt;
    Button replay, endgame;

    ImageView d_head;
    ImageView d_stomach;
    ImageView d_armleft;
    ImageView d_armright;
    ImageView d_legleft;
    ImageView d_legright;

    Button a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,vb,w,x,y,z,æ,ø,å;
    MediaPlayer soundwon;

    SharedPreferences shared;
    Timer timer;
    int time = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.play_activity);

        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("SecretWord")!= null) {
            logic.setSecretWord(bundle.getString("SecretWord"));
        }

        /* Load Shared Preferences Data */
        shared = getSharedPreferences("NumberOfGames", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        int NoGames = shared.getInt("NumberOfGames", 0);
        editor.putInt("NumberOfGames", NoGames+1);
        editor.commit();

        /* GameScore */
        tvgamescore = (TextView) findViewById(R.id.play_gamescore);
        tvgamescoretxt = (TextView) findViewById(R.id.play_gamescore_txt);
        tvgamescore.setText("");
        tvgamescoretxt.setText("");

        tvhighscore = (TextView) findViewById(R.id.play_highscore);
        tvhighscoretxt = (TextView) findViewById(R.id.play_highscore_txt);
        tvhighscoretxt.setText("Time");

        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        tvhighscore.setText(""+time);
                        time++;
                    }
                });
            }
        }, 1000, 1000);

        info = (TextView) findViewById(R.id.info);

        /* Set the parts to invisible */
        makeHangManInvisible();

        /* Assign Buttons */
        makeButtons();
        makeHandlers();

        /* Assign the textview */
        info = (TextView) findViewById(R.id.info);

        /* Sound */
        soundwon = MediaPlayer.create(this, R.raw.ucanttouchthis);

        updateScreen();

    }


    private void updateScreen() {
        info.setText("Guess the word:\n " + logic.getViewWord());
        info.append("\n\nTries " + logic.getWrongs() + "/6 \n\n Your Entries: \n" + logic.getUsedLetters());

        if (logic.getWrongs() == 1) {
            d_head.setVisibility(View.VISIBLE);
        } else if (logic.getWrongs() == 2) {
            d_stomach.setVisibility(View.VISIBLE);
        } else if (logic.getWrongs() == 3) {
            d_armleft.setVisibility(View.VISIBLE);
        } else if (logic.getWrongs() == 4) {
            d_armright.setVisibility(View.VISIBLE);
        } else if (logic.getWrongs() == 5) {
            d_legleft.setVisibility(View.VISIBLE);
        } else if (logic.getWrongs() == 6) {
            d_legright.setVisibility(View.VISIBLE);
        }

        /* Game was won */
        if (logic.isGameWon()) {
            timer.cancel();
            soundwon.start();
            info.setText("You've guessed the word, so the animal gets to live!\n");
            info.append("The correct word was \"" + logic.getWord() + "\".\nYou guessed the word in "+time+" seconds");

            /* Make Buttons disappear and show replay button*/
            hideButtons();
            replay.setVisibility(View.VISIBLE);
            endgame.setVisibility(View.VISIBLE);
        }
        /* Game was lost */
        if (logic.isGameLost()) {
            timer.cancel();
            info.setText("You have lost, the word was \n\n" + logic.getWord());
            replay.setVisibility(View.VISIBLE);
            endgame.setVisibility(View.VISIBLE);

            /* Make top row disappear and show replay button*/
            hideButtons();
            replay.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        System.out.println("Click");
        if (a.isPressed()) {
            logic.gætBogstav("a");
            a.setVisibility(View.INVISIBLE);
        } else if (b.isPressed()) {
            logic.gætBogstav("b");
            b.setVisibility(View.INVISIBLE);
        } else if (c.isPressed()) {
            logic.gætBogstav("c");
            c.setVisibility(View.INVISIBLE);
        } else if (d.isPressed()) {
            logic.gætBogstav("d");
            d.setVisibility(View.INVISIBLE);
        } else if (e.isPressed()) {
            logic.gætBogstav("e");
            e.setVisibility(View.INVISIBLE);
        } else if (f.isPressed()) {
            logic.gætBogstav("f");
            f.setVisibility(View.INVISIBLE);
        } else if (g.isPressed()) {
            logic.gætBogstav("g");
            g.setVisibility(View.INVISIBLE);
        } else if (h.isPressed()) {
            logic.gætBogstav("h");
            h.setVisibility(View.INVISIBLE);
        } else if (i.isPressed()) {
            logic.gætBogstav("i");
            i.setVisibility(View.INVISIBLE);
        } else if (j.isPressed()) {
            logic.gætBogstav("j");
            j.setVisibility(View.INVISIBLE);
        } else if (k.isPressed()) {
            logic.gætBogstav("k");
            k.setVisibility(View.INVISIBLE);
        } else if (l.isPressed()) {
            logic.gætBogstav("l");
            l.setVisibility(View.INVISIBLE);
        } else if (m.isPressed()) {
            logic.gætBogstav("m");
            m.setVisibility(View.INVISIBLE);
        } else if (n.isPressed()) {
            logic.gætBogstav("n");
            n.setVisibility(View.INVISIBLE);
        } else if (o.isPressed()) {
            logic.gætBogstav("o");
            o.setVisibility(View.INVISIBLE);
        } else if (p.isPressed()) {
            logic.gætBogstav("p");
            p.setVisibility(View.INVISIBLE);
        } else if (q.isPressed()) {
            logic.gætBogstav("q");
            q.setVisibility(View.INVISIBLE);
        } else if (r.isPressed()) {
            logic.gætBogstav("r");
            r.setVisibility(View.INVISIBLE);
        } else if (s.isPressed()) {
            logic.gætBogstav("s");
            s.setVisibility(View.INVISIBLE);
        } else if (t.isPressed()) {
            logic.gætBogstav("t");
            t.setVisibility(View.INVISIBLE);
        } else if (u.isPressed()) {
            logic.gætBogstav("u");
            u.setVisibility(View.INVISIBLE);
        } else if (vb.isPressed()) {
            logic.gætBogstav("v");
            vb.setVisibility(View.INVISIBLE);
        } else if (w.isPressed()) {
            logic.gætBogstav("w");
            w.setVisibility(View.INVISIBLE);
        } else if (x.isPressed()) {
            logic.gætBogstav("x");
            x.setVisibility(View.INVISIBLE);
        } else if (y.isPressed()) {
            logic.gætBogstav("y");
            y.setVisibility(View.INVISIBLE);
        } else if (z.isPressed()) {
            logic.gætBogstav("z");
            z.setVisibility(View.INVISIBLE);
        } else if (æ.isPressed()) {
            logic.gætBogstav("æ");
            æ.setVisibility(View.INVISIBLE);
        } else if (ø.isPressed()) {
            logic.gætBogstav("ø");
            ø.setVisibility(View.INVISIBLE);
        } else if (å.isPressed()) {
            logic.gætBogstav("å");
            å.setVisibility(View.INVISIBLE);
        }

        if (replay.isPressed()) {
            soundwon.stop();
            Intent intent = new Intent(PlayMultiActivity.this, MultiplayerActivity.class);
            finish();
            startActivity(intent);
        }
        if (endgame.isPressed()) {
            soundwon.stop();
            Intent intent = new Intent(PlayMultiActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }
        updateScreen();
    }

    private void makeButtons() {
        replay = (Button) findViewById(R.id.btn_replay);
        replay.setVisibility(View.GONE);
        replay.setOnClickListener(this);
        endgame = (Button) findViewById(R.id.btn_endgame);
        endgame.setVisibility(View.GONE);
        endgame.setOnClickListener(this);

        a = (Button) findViewById(R.id.a);
        b = (Button) findViewById(R.id.b);
        c = (Button) findViewById(R.id.c);
        d = (Button) findViewById(R.id.d);
        e = (Button) findViewById(R.id.e);
        f = (Button) findViewById(R.id.f);
        g = (Button) findViewById(R.id.g);
        h = (Button) findViewById(R.id.h);
        i = (Button) findViewById(R.id.i);
        j = (Button) findViewById(R.id.j);
        k = (Button) findViewById(R.id.k);
        l = (Button) findViewById(R.id.l);
        m = (Button) findViewById(R.id.m);
        n = (Button) findViewById(R.id.n);
        o = (Button) findViewById(R.id.o);
        p = (Button) findViewById(R.id.p);
        q = (Button) findViewById(R.id.q);
        r = (Button) findViewById(R.id.r);
        s = (Button) findViewById(R.id.s);
        t = (Button) findViewById(R.id.t);
        u = (Button) findViewById(R.id.u);
        vb = (Button) findViewById(R.id.v);
        w = (Button) findViewById(R.id.w);
        x = (Button) findViewById(R.id.x);
        y = (Button) findViewById(R.id.y);
        z = (Button) findViewById(R.id.z);
        æ = (Button) findViewById(R.id.æ);
        ø = (Button) findViewById(R.id.ø);
        å = (Button) findViewById(R.id.å);
    }
    private void hideButtons() {
        a.setVisibility(View.GONE);
        b.setVisibility(View.GONE);
        c.setVisibility(View.GONE);
        d.setVisibility(View.GONE);
        e.setVisibility(View.GONE);
        f.setVisibility(View.GONE);
        g.setVisibility(View.GONE);
        h.setVisibility(View.INVISIBLE);
        i.setVisibility(View.INVISIBLE);
        j.setVisibility(View.INVISIBLE);
        k.setVisibility(View.INVISIBLE);
        l.setVisibility(View.INVISIBLE);
        m.setVisibility(View.INVISIBLE);
        n.setVisibility(View.INVISIBLE);
        o.setVisibility(View.INVISIBLE);
        p.setVisibility(View.INVISIBLE);
        q.setVisibility(View.INVISIBLE);
        r.setVisibility(View.INVISIBLE);
        s.setVisibility(View.INVISIBLE);
        t.setVisibility(View.INVISIBLE);
        u.setVisibility(View.INVISIBLE);
        vb.setVisibility(View.INVISIBLE);
        w.setVisibility(View.INVISIBLE);
        x.setVisibility(View.INVISIBLE);
        y.setVisibility(View.INVISIBLE);
        z.setVisibility(View.INVISIBLE);
        æ.setVisibility(View.INVISIBLE);
        ø.setVisibility(View.INVISIBLE);
        å.setVisibility(View.INVISIBLE);
    }

    private void makeHandlers() {
        a.setOnClickListener(this);
        b.setOnClickListener(this);
        c.setOnClickListener(this);
        d.setOnClickListener(this);
        e.setOnClickListener(this);
        f.setOnClickListener(this);
        g.setOnClickListener(this);
        h.setOnClickListener(this);
        i.setOnClickListener(this);
        j.setOnClickListener(this);
        k.setOnClickListener(this);
        l.setOnClickListener(this);
        m.setOnClickListener(this);
        n.setOnClickListener(this);
        o.setOnClickListener(this);
        p.setOnClickListener(this);
        q.setOnClickListener(this);
        r.setOnClickListener(this);
        s.setOnClickListener(this);
        t.setOnClickListener(this);
        u.setOnClickListener(this);
        vb.setOnClickListener(this);
        w.setOnClickListener(this);
        x.setOnClickListener(this);
        y.setOnClickListener(this);
        z.setOnClickListener(this);
        æ.setOnClickListener(this);
        ø.setOnClickListener(this);
        å.setOnClickListener(this);
    }

    private void makeHangManInvisible() {
        d_head = (ImageView) findViewById(R.id.d_head);
        d_stomach = (ImageView) findViewById(R.id.d_stomach);
        d_armleft = (ImageView) findViewById(R.id.d_leftarm);
        d_armright = (ImageView) findViewById(R.id.d_rightarm);
        d_legleft = (ImageView) findViewById(R.id.d_leftleg);
        d_legright = (ImageView) findViewById(R.id.d_rightleg);

        /* Get Shared Preferences Animal */
        SharedPreferences shared = getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");
        if (animal.equals("SheepWhite")) {
            d_head.setImageResource(R.drawable.sheep_white_head);
            d_stomach.setImageResource(R.drawable.sheep_white_stomach);
            d_armleft.setImageResource(R.drawable.sheep_white_armleft);
            d_armright.setImageResource(R.drawable.sheep_white_armright);
            d_legleft.setImageResource(R.drawable.sheep_legleft);
            d_legright.setImageResource(R.drawable.sheep_legright);
        } else if (animal.equals("SheepPink")) {
            d_head.setImageResource(R.drawable.sheep_pink_head);
            d_stomach.setImageResource(R.drawable.sheep_pink_stomach);
            d_armleft.setImageResource(R.drawable.sheep_pink_armleft);
            d_armright.setImageResource(R.drawable.sheep_pink_armright);
            d_legleft.setImageResource(R.drawable.sheep_legleft);
            d_legright.setImageResource(R.drawable.sheep_legright);
        } else if (animal.equals("BunnyBlue")) {
            d_head.setImageResource(R.drawable.bunny_blue_head);
            d_stomach.setImageResource(R.drawable.bunny_blue_stomach);
            d_armleft.setImageResource(R.drawable.bunny_blue_armleft);
            d_armright.setImageResource(R.drawable.bunny_blue_armright);
            d_legleft.setImageResource(R.drawable.bunny_blue_legleft);
            d_legright.setImageResource(R.drawable.bunny_blue_legright);
        } else if (animal.equals("DragonGreen")) {
            d_head.setImageResource(R.drawable.dragon_green_head);
            d_stomach.setImageResource(R.drawable.dragon_green_stomach);
            d_armleft.setImageResource(R.drawable.dragon_green_armleft);
            d_armright.setImageResource(R.drawable.dragon_green_armright);
            d_legleft.setImageResource(R.drawable.dragon_green_legleft);
            d_legright.setImageResource(R.drawable.dragon_green_legright);
        }

        /* Make Animal Invisible */
        d_head.setVisibility(View.INVISIBLE);
        d_stomach.setVisibility(View.INVISIBLE);
        d_armleft.setVisibility(View.INVISIBLE);
        d_armright.setVisibility(View.INVISIBLE);
        d_legleft.setVisibility(View.INVISIBLE);
        d_legright.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        soundwon.stop();
                        Intent intent = new Intent(PlayMultiActivity.this, MainMenuActivity.class);
                        startActivity(intent);
                    }
                }).create().show();
    }
}