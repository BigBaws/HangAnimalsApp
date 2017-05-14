package com.bigbaws.hanganimals.backend.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.dao.RESTConnector;
import com.bigbaws.hanganimals.backend.exceptions.DAOException;
import com.bigbaws.hanganimals.backend.user.User;
import com.bigbaws.hanganimals.frontend.MultiplayerActivity;
import com.bigbaws.hanganimals.frontend.PlayMultiActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Silas on 12-05-2017.
 */

public class GameRoomsCustomAdapter extends BaseAdapter implements ListAdapter {

    private ProgressDialog progressDialog;
    private ArrayList<String> list = new ArrayList<String>();
    private Context context;
    private String roomID;
//    private int activeUsers;



    public GameRoomsCustomAdapter(ArrayList<String> list, Context context) {
        this.list = list;
        this.context = context;
//        this.activeUsers = activeUsers;


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int pos) {
        return list.get(pos);
    }

    @Override
    public long getItemId(int pos) {
//        return list.get(pos).getId();
        return 0;
        //just return 0 if your list items do not have an Id variable.
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.rooms_list, null);
        }

        //Handle TextView and display string from your list
        final TextView listItemText = (TextView)view.findViewById(R.id.list_item_string);
//        final TextView activeUsers = (TextView) view.findViewById(R.id.list_item_string4);

//        activeUsers.setText(activeUsers+"");
        listItemText.setText(list.get(position));

        //Handle buttons and add onClickListeners
        Button joinBtn = (Button)view.findViewById(R.id.join_btn);

        joinBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                roomID = listItemText.getText().toString();
                joinGameAsync();
//                listenAsync();
//                Toast.makeText(context, listItemText.getText(), Toast.LENGTH_SHORT).show();

                // Start new multiplayer game

                notifyDataSetChanged();
            }
        });

        return view;
    }


    public void joinGameAsync() {
        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {


                try {

                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("token", params[0])
                            .appendQueryParameter("userid", params[1]);
                    String encodedParams = builder.build().getEncodedQuery();

                    Log.e("Join game", encodedParams);

                    return RESTConnector.POSTQuery(encodedParams, params[2]);


                }catch (Exception e) {
                    e.printStackTrace();
                }



                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(context, "Please wait", "Attempting to join room");

            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {
                progressDialog.dismiss();

                Intent intent = new Intent(context, PlayMultiActivity.class);
                intent.putExtra("roomid", roomID);
                context.startActivity(intent);



                System.out.println("JOIN GAME OBJECT = " + jsonObject);



            }


        }.execute(User.token, User.id, "/multiplayer/room/" + roomID + "/join");
    }



}