package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.logic.GameLogic;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class PlayFragment extends Fragment implements View.OnClickListener {

    GameLogic logic = new GameLogic();

    TextView info, tvhighscore, tvgamescore;
    Button replay;

    ImageView d_head;
    ImageView d_stomach;
    ImageView d_armleft;
    ImageView d_armright;
    ImageView d_legleft;
    ImageView d_legright;

    Button a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,vb,w,x,y,z,æ,ø,å;
    MediaPlayer soundwon;

    SharedPreferences shared;
    CountDownTimer timer;
    Long gamescore, highscore;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.play_fragment, container, false);

        /* Load Shared Preferences Data */
        shared = this.getActivity().getSharedPreferences("NumberOfGames", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        int NoGames = shared.getInt("NumberOfGames", 0);
        editor.putInt("NumberOfGames", NoGames+1);
        editor.commit();

        /* GameScore */
        tvgamescore = (TextView) v.findViewById(R.id.play_gamescore);
        shared = this.getActivity().getSharedPreferences("Highscore", Context.MODE_PRIVATE);
        highscore = (long) shared.getInt("Highscore", 0);
        tvhighscore = (TextView) v.findViewById(R.id.play_highscore);
        tvhighscore.setText(highscore+"");

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


        info = (TextView) v.findViewById(R.id.info);

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
        makeHangManInvisible(v);

        /* Assign Buttons */
        makeButtons(v);

        /* Assign the textview */
        info = (TextView) v.findViewById(R.id.info);

        /* Sound */
//        soundwon = MediaPlayer.create(R.raw.ucanttouchthis);

        updateScreen();
        return v;
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
            info.setText("You've guessed the word, so the animal gets to live!\n");
            if (gamescore > highscore) {
                info.append("The correct word was \"" + logic.getWord() + "\".\nYour new highscore is "+gamescore);
            } else {
                info.append("The correct word was \"" + logic.getWord() + "\".\nThe gamescore is "+gamescore);
            }

            /* Won */
            shared = this.getActivity().getSharedPreferences("Won", Context.MODE_PRIVATE);
            SharedPreferences.Editor ewon = shared.edit();
            int won = shared.getInt("Won", 0);
            ewon.putInt("Won", won+1);
            ewon.commit();

            /* Win Streak */
            shared = this.getActivity().getSharedPreferences("WinStreak", Context.MODE_PRIVATE);
            SharedPreferences.Editor ewin = shared.edit();
            int streak = shared.getInt("WinStreak", 0);
            ewin.putInt("WinStreak", streak+1);
            ewin.commit();

            /* Highscore */
            shared = this.getActivity().getSharedPreferences("Highscore", Context.MODE_PRIVATE);
            long highscore = shared.getInt("Highscore", 0);
            if (gamescore > highscore) {
                SharedPreferences.Editor ehigh = shared.edit();
                ehigh.putInt("Highscore", gamescore.intValue());
                ehigh.commit();
            }

            /* Make Buttons disappear and show replay button*/
            hideButtons();
            replay.setVisibility(View.VISIBLE);
        }
        /* Game was lost */
        if (logic.isGameLost()) {
            timer.cancel();
            info.setText("You have lost, the word was \n\n" + logic.getWord());
            replay.setVisibility(View.VISIBLE);

            /* Win Streak */
            shared = this.getActivity().getSharedPreferences("WinStreak", Context.MODE_PRIVATE);
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
//            soundwon.stop();
            Fragment playfrag = new PlayFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .replace(R.id.fragment_body, playfrag)
                    .commit();
        }

        updateScreen();
    }

    private void makeButtons(View v) {
        replay = (Button) v.findViewById(R.id.btn_replay);
        replay.setVisibility(View.GONE);
        replay.setOnClickListener(this);

        a = (Button) v.findViewById(R.id.a);
        b = (Button) v.findViewById(R.id.b);
        c = (Button) v.findViewById(R.id.c);
        d = (Button) v.findViewById(R.id.d);
        e = (Button) v.findViewById(R.id.e);
        f = (Button) v.findViewById(R.id.f);
        g = (Button) v.findViewById(R.id.g);
        h = (Button) v.findViewById(R.id.h);
        i = (Button) v.findViewById(R.id.i);
        j = (Button) v.findViewById(R.id.j);
        k = (Button) v.findViewById(R.id.k);
        l = (Button) v.findViewById(R.id.l);
        m = (Button) v.findViewById(R.id.m);
        n = (Button) v.findViewById(R.id.n);
        o = (Button) v.findViewById(R.id.o);
        p = (Button) v.findViewById(R.id.p);
        q = (Button) v.findViewById(R.id.q);
        r = (Button) v.findViewById(R.id.r);
        s = (Button) v.findViewById(R.id.s);
        t = (Button) v.findViewById(R.id.t);
        u = (Button) v.findViewById(R.id.u);
        vb = (Button) v.findViewById(R.id.v);
        w = (Button) v.findViewById(R.id.w);
        x = (Button) v.findViewById(R.id.x);
        y = (Button) v.findViewById(R.id.y);
        z = (Button) v.findViewById(R.id.z);
        æ = (Button) v.findViewById(R.id.æ);
        ø = (Button) v.findViewById(R.id.ø);
        å = (Button) v.findViewById(R.id.å);
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

    private void makeHangManInvisible(View v) {
        d_head = (ImageView) v.findViewById(R.id.d_head);
        d_stomach = (ImageView) v.findViewById(R.id.d_stomach);
        d_armleft = (ImageView) v.findViewById(R.id.d_leftarm);
        d_armright = (ImageView) v.findViewById(R.id.d_rightarm);
        d_legleft = (ImageView) v.findViewById(R.id.d_leftleg);
        d_legright = (ImageView) v.findViewById(R.id.d_rightleg);

        /* Get Shared Preferences Animal */
        SharedPreferences shared = this.getActivity().getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");
        if (animal == "SheepWhite") {
            d_head.setImageResource(R.drawable.sheep_white_head);
            d_stomach.setImageResource(R.drawable.sheep_white_stomach);
            d_armleft.setImageResource(R.drawable.sheep_white_armleft);
            d_armright.setImageResource(R.drawable.sheep_white_armright);
            d_legleft.setImageResource(R.drawable.sheep_legleft);
            d_legright.setImageResource(R.drawable.sheep_legright);
        } else if (animal == "SheepPink") {
            d_head.setImageResource(R.drawable.sheep_pink_head);
            d_stomach.setImageResource(R.drawable.sheep_pink_stomach);
            d_armleft.setImageResource(R.drawable.sheep_pink_armleft);
            d_armright.setImageResource(R.drawable.sheep_pink_armright);
            d_legleft.setImageResource(R.drawable.sheep_legleft);
            d_legright.setImageResource(R.drawable.sheep_legright);
        } else if (animal == "BunnyBlue") {
            d_head.setImageResource(R.drawable.bunny_blue_head);
            d_stomach.setImageResource(R.drawable.bunny_blue_stomach);
            d_armleft.setImageResource(R.drawable.bunny_blue_armleft);
            d_armright.setImageResource(R.drawable.bunny_blue_armright);
            d_legleft.setImageResource(R.drawable.bunny_blue_legleft);
            d_legright.setImageResource(R.drawable.bunny_blue_legright);
        } else if (animal == "DragonGreen") {
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
}