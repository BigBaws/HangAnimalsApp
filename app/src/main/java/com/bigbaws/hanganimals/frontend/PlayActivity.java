package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.logic.GameLogic;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class PlayActivity extends AppCompatActivity implements View.OnClickListener {

    GameLogic logic = new GameLogic();

    TextView info, tvhighscore, tvgamescore, tvcombo;
    ImageView comboimage;
    Button replay, endgame;

    ImageView d_head, d_stomach, d_armleft, d_armright, d_legleft, d_legright;

    Button a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,vb,w,x,y,z,æ,ø,å;
    MediaPlayer soundwon, soundcombo, soundplay;

    SharedPreferences shared;
    CountDownTimer timer;
    Long gamescore, highscore;
    int combo = 0;
    int correct = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.play_activity);

        /* Load Shared Preferences Data */
        shared = getSharedPreferences("NumberOfGames", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        int NoGames = shared.getInt("NumberOfGames", 0);
        editor.putInt("NumberOfGames", NoGames+1);
        editor.commit();

        /* GameScore & Combo */
        tvgamescore = (TextView) findViewById(R.id.play_gamescore);
        shared = getSharedPreferences("Highscore", Context.MODE_PRIVATE);
        highscore = (long) shared.getInt("Highscore", 0);
        tvhighscore = (TextView) findViewById(R.id.play_highscore);
        tvhighscore.setText(highscore+"");
        tvcombo = (TextView) findViewById(R.id.play_combo);
        comboimage = (ImageView) findViewById(R.id.play_combo_image);
        comboimage.setImageResource(0);

        timer = new CountDownTimer(120000, 1000) { // adjust the milli seconds here
            public void onTick(long millisUntilFinished) {
                if (logic.getWord() != null) {
                    tvgamescore.setText("" + (millisUntilFinished * logic.getWord().length() - (logic.getWrongs() * 10000)));
                    gamescore = millisUntilFinished * logic.getWord().length() - (logic.getWrongs() * 10000);
                }
            }
            public void onFinish() {
                tvgamescore.setText("0");
            }
        }.start();


        info = (TextView) findViewById(R.id.info);

        new AsyncTask() {
            @Override
            protected Object doInBackground(Object... arg0) {
                try {
                    logic.hentOrdFraDr();
                    return "Ordene blev korrekt hentet fra DR's server \n";
                } catch (Exception e) {
                    e.printStackTrace();
                    return "Ordene blev ikke hentet korrekt: \n"+e;
                }
            }


            @Override
            protected void onPostExecute(Object resultat) {
                logic.nulstil();
                info.setText("Guess the word:\n " + logic.getViewWord());
                /* Assign Button handlers */
                makeHandlers();
            }
        }.execute();

        /* Set the parts to invisible */
        makeHangManInvisible();

        /* Assign Buttons */
        makeButtons();

        /* Assign the textview */
        info = (TextView) findViewById(R.id.info);

        /* Sound */
        soundwon = MediaPlayer.create(this, R.raw.ucanttouchthis);
        soundplay = MediaPlayer.create(this, R.raw.play);
        soundplay.start();

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
            tvcombo.setText(""+ ++combo);
            timer.cancel();
            soundwon.start();
            info.setText("You've guessed the word, so the animal gets to live!\n");
            if (gamescore > highscore) {
                info.append("The correct word was \"" + logic.getWord() + "\".\nYour new highscore is "+gamescore);
            } else {
                info.append("The correct word was \"" + logic.getWord() + "\".\nThe gamescore is "+gamescore);
            }

            /* Won */
            shared = getSharedPreferences("Won", Context.MODE_PRIVATE);
            SharedPreferences.Editor ewon = shared.edit();
            int won = shared.getInt("Won", 0);
            ewon.putInt("Won", won+1);
            ewon.commit();

            /* Win Streak */
            shared = getSharedPreferences("WinStreak", Context.MODE_PRIVATE);
            SharedPreferences.Editor ewin = shared.edit();
            int streak = shared.getInt("WinStreak", 0);
            ewin.putInt("WinStreak", streak+1);
            ewin.commit();

            /* Highscore */
            shared = getSharedPreferences("Highscore", Context.MODE_PRIVATE);
            long highscore = shared.getInt("Highscore", 0);
            if (gamescore > highscore) {
                SharedPreferences.Editor ehigh = shared.edit();
                ehigh.putInt("Highscore", gamescore.intValue());
                ehigh.commit();
            }

            /* Combo */
            shared = getSharedPreferences("Combo", Context.MODE_PRIVATE);
            SharedPreferences.Editor ecom = shared.edit();
            ecom.putInt("Combo", combo);
            ecom.commit();

            /* Make Buttons disappear and show replay button*/
            hideButtons();
            replay.setVisibility(View.VISIBLE);
            endgame.setVisibility(View.VISIBLE);
        }
        /* Game was lost */
        if (logic.isGameLost()) {
            combo = 0;
            timer.cancel();
            info.setText("You have lost, the word was \n\n" + logic.getWord());
            replay.setVisibility(View.VISIBLE);
            endgame.setVisibility(View.VISIBLE);

            /* Win Streak */
            shared = getSharedPreferences("WinStreak", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = shared.edit();
            editor.putInt("WinStreak", 0);
            editor.commit();


            /* Make top row disappear and show replay button*/
            hideButtons();
            replay.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
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
            recreate();

        }
        if (endgame.isPressed()) {
            soundwon.stop();
            finish();
        }

        /* COMBO */
        System.out.println("**************** WORD!!!! **************");
        System.out.println(logic.getWord());
        System.out.println(logic.isLastCorrect());
        if (logic.isLastCorrect()) {
            if (!logic.isGameOver()) {
                combo++;
                if (combo > 1) {
                    if (combo == 2) {
                        soundcombo = MediaPlayer.create(this, R.raw.dominating);
                        soundcombo.start();
                    } else if (combo == 3) {
                        soundcombo = MediaPlayer.create(this, R.raw.unstoppable);
                        soundcombo.start();
                    } else if (combo == 3) {
                        soundcombo = MediaPlayer.create(this, R.raw.wickedsick);
                        soundcombo.start();
                    } else if (combo == 4) {
                        soundcombo = MediaPlayer.create(this, R.raw.rampage);
                        soundcombo.start();
                    } else if (combo == 5) {
                        soundcombo = MediaPlayer.create(this, R.raw.holyshit);
                        soundcombo.start();
                    } else if (combo > 6) {
                        soundcombo = MediaPlayer.create(this, R.raw.godlike);
                        soundcombo.start();
                    }
                    comboimage.setImageResource(R.drawable.combo);
                    Animation fadein = AnimationUtils.loadAnimation(this, R.anim.combo);
                    comboimage.startAnimation(fadein);
                    comboimage.setVisibility(View.INVISIBLE);
                }
            }
        } else {
            combo = 0;
        }
        tvcombo.setText(""+combo);

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
        } else if (animal.equals("SheepBlack")) {
            d_head.setImageResource(R.drawable.sheep_black_head);
            d_stomach.setImageResource(R.drawable.sheep_black_stomach);
            d_armleft.setImageResource(R.drawable.sheep_black_armleft);
            d_armright.setImageResource(R.drawable.sheep_black_armright);
            d_legleft.setImageResource(R.drawable.sheep_legleft);
            d_legright.setImageResource(R.drawable.sheep_legright);
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
                        finish();
                    }
                }).create().show();
    }
}