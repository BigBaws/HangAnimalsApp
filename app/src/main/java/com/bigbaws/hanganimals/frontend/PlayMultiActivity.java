package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.dao.RESTConnector;
import com.bigbaws.hanganimals.backend.exceptions.DAOException;
import com.bigbaws.hanganimals.backend.user.User;
import com.bigbaws.hanganimals.backend.util.ActivePlayersCustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class PlayMultiActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView competitors_listView;
    private String roomid;
    private ArrayList<String> competitors =  new ArrayList<String>();
    private ArrayList<String> competitors_score_list =  new ArrayList<String>();
    private final Handler handler = new Handler();
    private boolean activityIsActive = true;
    private TextView wordToGuess;
    private Button replay, endgame;
    private ImageView d_head;
    private ImageView d_stomach;
    private ImageView d_armleft;
    private ImageView d_armright;
    private ImageView d_legleft;
    private ImageView d_legright;
    private Button a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,vb,w,x,y,z,æ,ø,å;
    private MediaPlayer soundwon;
    private SharedPreferences shared;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.play_multi_activity);


        Bundle bundle = getIntent().getExtras();
        if(bundle.getString("roomid")!= null) {
            roomid = bundle.getString("roomid");
        }

        /* Retrieves the hidden word to be shown on screen */
        getHiddenWordAsync();

        /* Makes a call every 5 seconds to update list of active players in a room */
        handler.postDelayed(new Runnable()   {
            @Override
            public void run() {

                if(activityIsActive) {
                    Log.e("Update", "Active Users Updated");
                    getActiveUsersAsync();
                    handler.postDelayed(this, 5000);
                }else {
                    handler.removeCallbacks(this);
                }
            }
        }, 0);


        /* Load Shared Preferences Data */
        shared = getSharedPreferences("NumberOfGames", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        int NoGames = shared.getInt("NumberOfGames", 0);
        editor.putInt("NumberOfGames", NoGames+1);
        editor.commit();


        /* Set the parts to invisible */
        makeHangManInvisible();

        /* Assign Buttons */
        makeButtons();
        makeHandlers();

        /* Assign TextView */
        wordToGuess = (TextView) findViewById(R.id.info);


        /* Sound */
        soundwon = MediaPlayer.create(this, R.raw.ucanttouchthis);

//        updateScreen();
    }


    private void updateScreen() {
//



//
//        /* Game was won */
//        if (logic.isGameWon()) {
//            soundwon.start();
////            info.setText("You've guessed the word, so the animal gets to live!\n");
////            info.append("The correct word was \"" + logic.getWord() + "\".\nYou guessed the word in "+time+" seconds");
//
//            /* Make Buttons disappear and show replay button*/
//            hideButtons();
//            replay.setVisibility(View.VISIBLE);
//            endgame.setVisibility(View.VISIBLE);
//        }
//        /* Game was lost */
//        if (logic.isGameLost()) {
////            timer.cancel();
////            info.setText("You have lost, the word was \n\n" + logic.getWord());
//            replay.setVisibility(View.VISIBLE);
//            endgame.setVisibility(View.VISIBLE);
//
//            /* Make top row disappear and show replay button*/
//            hideButtons();
//            replay.setVisibility(View.VISIBLE);
//        }
//
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
    public void getActiveUsersAsync() {


        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {


                try {
                    return RESTConnector.GETQuery("/multiplayer/room/" + roomid + "/users?token=" + User.token);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
//                progressDialog.dismiss();


                if(jsonObject == null) {
                    Log.e("GET ACTIVE USERS", "Object is null");
                }else {

                    try {
                        JSONArray array = jsonObject.getJSONArray("users");
                        System.out.println("ACTIVE USERS ARRAY: " + array);

                        competitors.clear();
                        competitors_score_list.clear();

                        for (int i = 0; i < array.length(); i++) {

                            String contester_id = array.getJSONObject(i).getString("name");
                            String contester_score = array.getJSONObject(i).getString("gamescore");


                            competitors.add(contester_id);
                            competitors_score_list.add(contester_score);


                        }



                        ActivePlayersCustomAdapter adapter = new ActivePlayersCustomAdapter(competitors, competitors_score_list, PlayMultiActivity.this);
                        competitors_listView = (ListView) findViewById(R.id.contesters_list);
                        competitors_listView.setAdapter(adapter);



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



            }


        }.execute();


    }
    public void leaveRoomAsync() {
        activityIsActive = false;
        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {


                try {

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("token", params[0])
                            .appendQueryParameter("userid", params[1]);
                    String encodedParams = builder.build().getEncodedQuery();

                    Log.e("leave room", encodedParams);

                    return RESTConnector.POSTQuery(encodedParams, params[2]);


                }catch (Exception e) {
                    e.printStackTrace();
                }



                return null;
            }

            @Override
            protected void onPreExecute() {
//                progressDialog = ProgressDialog.show(context,
//                        "Please wait",
//                        "Attempting to join room");

            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
//                progressDialog.dismiss();

                System.out.println("LEAVE ROOM RETURN OBJECT = " + jsonObject);



            }


        }.execute(User.token, User.id, "/multiplayer/room/" + roomid + "/leave");
    }

    public void guessLetterAsync(String letter) {
        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {


                try {

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("token", params[0])
                            .appendQueryParameter("userid", params[1])
                            .appendQueryParameter("letter", params[2]);
                    String encodedParams = builder.build().getEncodedQuery();

                    Log.e("leave room", encodedParams);

                    return RESTConnector.POSTQuery(encodedParams, params[3]);


                }catch (Exception e) {
                    e.printStackTrace();
                }



                return null;
            }

            @Override
            protected void onPreExecute() {

            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                System.out.println("GuessLetterAsync " + jsonObject);

                /* Below code updates screen and checks wether or not a game is won */
                try {
                    String hiddenWord = jsonObject.getString("word");
                    int wrongs = Integer.parseInt(jsonObject.getString("wrongs"));

                    /* Update hidden word whenever a guess is made */
                    wordToGuess.setText("Guess the word below before your friends!\n\n" + hiddenWord);

                    if (wrongs == 1) {
                        d_head.setVisibility(View.VISIBLE);
                    } else if (wrongs == 2) {
                        d_stomach.setVisibility(View.VISIBLE);
                    } else if (wrongs == 3) {
                        d_armleft.setVisibility(View.VISIBLE);
                    } else if (wrongs == 4) {
                        d_armright.setVisibility(View.VISIBLE);
                    } else if (wrongs == 5) {
                        d_legleft.setVisibility(View.VISIBLE);
                    } else if (wrongs == 6) {
                        d_legright.setVisibility(View.VISIBLE);
                    }

//                    if(wrongs == 6) {
//                        wordToGuess.setText("You didn't guess the word :(\n\n" + hiddenWord);
//                        replay.setVisibility(View.VISIBLE);
//                        endgame.setVisibility(View.VISIBLE);
//
//                        /* Make top row disappear and show replay button*/
//                        hideButtons();
//                        replay.setVisibility(View.VISIBLE);
//                    }


                } catch (JSONException e1) {
                    e1.printStackTrace();
                }


            }


        }.execute(User.token, User.id, letter, "/multiplayer/room/" + roomid + "/guess");
    }
    public void getHiddenWordAsync() {

        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {
                    return RESTConnector.GETQuery("/multiplayer/room/" + roomid + "/userword?token=" + User.token + "&userid=" + User.id);
                } catch (DAOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {


            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                if(jsonObject == null) {
                    Log.e("GetHiddenWord", "Object is null");
                }else {

                    try {

                        String hiddenWord = jsonObject.getString("userword");
                        Log.e("hiddenWord", hiddenWord);

                        /* Set hidden work when game is started */
                        wordToGuess.setText("Guess the word below before your friends!\n" + hiddenWord);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }



            }


        }.execute();
    }



    @Override
    public void onClick(View v) {
        System.out.println("Click");
        if (a.isPressed()) {
            guessLetterAsync("a");
            a.setVisibility(View.INVISIBLE);
        } else if (b.isPressed()) {
            guessLetterAsync("b");
            b.setVisibility(View.INVISIBLE);
        } else if (c.isPressed()) {
            guessLetterAsync("c");
            c.setVisibility(View.INVISIBLE);
        } else if (d.isPressed()) {
            guessLetterAsync("d");
            d.setVisibility(View.INVISIBLE);
        } else if (e.isPressed()) {
            guessLetterAsync("e");
            e.setVisibility(View.INVISIBLE);
        } else if (f.isPressed()) {
            guessLetterAsync("f");
            f.setVisibility(View.INVISIBLE);
        } else if (g.isPressed()) {
            guessLetterAsync("g");
            g.setVisibility(View.INVISIBLE);
        } else if (h.isPressed()) {
            guessLetterAsync("h");
            h.setVisibility(View.INVISIBLE);
        } else if (i.isPressed()) {
            guessLetterAsync("i");
            i.setVisibility(View.INVISIBLE);
        } else if (j.isPressed()) {
            guessLetterAsync("j");
            j.setVisibility(View.INVISIBLE);
        } else if (k.isPressed()) {
            guessLetterAsync("k");
            k.setVisibility(View.INVISIBLE);
        } else if (l.isPressed()) {
            guessLetterAsync("l");
            l.setVisibility(View.INVISIBLE);
        } else if (m.isPressed()) {
            guessLetterAsync("m");
            m.setVisibility(View.INVISIBLE);
        } else if (n.isPressed()) {
            guessLetterAsync("n");
            n.setVisibility(View.INVISIBLE);
        } else if (o.isPressed()) {
            guessLetterAsync("o");
            o.setVisibility(View.INVISIBLE);
        } else if (p.isPressed()) {
            guessLetterAsync("p");
            p.setVisibility(View.INVISIBLE);
        } else if (q.isPressed()) {
            guessLetterAsync("q");
            q.setVisibility(View.INVISIBLE);
        } else if (r.isPressed()) {
            guessLetterAsync("r");
            r.setVisibility(View.INVISIBLE);
        } else if (s.isPressed()) {
            guessLetterAsync("s");
            s.setVisibility(View.INVISIBLE);
        } else if (t.isPressed()) {
            guessLetterAsync("t");
            t.setVisibility(View.INVISIBLE);
        } else if (u.isPressed()) {
            guessLetterAsync("u");
            u.setVisibility(View.INVISIBLE);
        } else if (vb.isPressed()) {
            guessLetterAsync("v");
            vb.setVisibility(View.INVISIBLE);
        } else if (w.isPressed()) {
            guessLetterAsync("w");
            w.setVisibility(View.INVISIBLE);
        } else if (x.isPressed()) {
            guessLetterAsync("x");
            x.setVisibility(View.INVISIBLE);
        } else if (y.isPressed()) {
            guessLetterAsync("y");
            y.setVisibility(View.INVISIBLE);
        } else if (z.isPressed()) {
            guessLetterAsync("z");
            z.setVisibility(View.INVISIBLE);
        } else if (æ.isPressed()) {
            guessLetterAsync("æ");
            æ.setVisibility(View.INVISIBLE);
        } else if (ø.isPressed()) {
            guessLetterAsync("ø");
            ø.setVisibility(View.INVISIBLE);
        } else if (å.isPressed()) {
            guessLetterAsync("å");
            å.setVisibility(View.INVISIBLE);
        }

        if (replay.isPressed()) {
            soundwon.stop();
            recreate();
        }
        if (endgame.isPressed()) {
            soundwon.stop();
            leaveRoomAsync();
            finish();

        }
    }


    @Override
    public void onBackPressed() {


        new AlertDialog.Builder(this)
                .setTitle("Warning!")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        activityIsActive = false;
                        leaveRoomAsync();

                        soundwon.stop();
                        finish();

                    }
                }).create().show();
    }




//    public void listenAsync() {
//
//        new AsyncTask<String, Void, JSONObject>() {
//
//            @Override
//            protected JSONObject doInBackground(String... params) {
//
//                try {
//                    return RESTConnector.GETQuery("/multiplayer/room/" + roomid + "/listen");
//                } catch (DAOException e) {
//                    e.printStackTrace();
//                }
//                return null;
//            }
//
//            @Override
//            protected void onPreExecute() {
//
//
//            }
//
//            @Override
//            protected void onPostExecute(JSONObject jsonObject) {
//
//                if(jsonObject == null) {
//                    Log.e("GetUserWord", "Object is null");
//                }else {
//
//
//                    Log.e("LISTEN", jsonObject.toString());
//
//
//                }
//
//
//
//            }
//
//
//        }.execute();
//    }


}