package com.bigbaws.hanganimals.backend.util;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;

import java.util.ArrayList;

/**
 * Created by Silas on 12-05-2017.
 */

public class ActivePlayersCustomAdapter extends BaseAdapter implements ListAdapter {

    private ProgressDialog progressDialog;
    private ArrayList<String> list = new ArrayList<String>();
    private ArrayList<String> contester_score = new ArrayList<>();
    private Context context;



    public ActivePlayersCustomAdapter(ArrayList<String> list, ArrayList<String> contester_score, Context context) {
        this.list = list;
        this.context = context;
        this.contester_score = contester_score;


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
            view = inflater.inflate(R.layout.competitors_list, null);
        }

        //Handle TextView and display string from your list
        final TextView contestant_userID = (TextView)view.findViewById(R.id.contester_id);
        final TextView contestant_score = (TextView)view.findViewById(R.id.contester_score);

        contestant_userID.setText(list.get(position));
        contestant_score.setText(contester_score.get(position));

        return view;
    }


//    public void activeUsersInRoom() {
//        new AsyncTask<String, Void, JSONObject>() {
//
//            @Override
//            protected JSONObject doInBackground(String... params) {
//
//
//                try {
//
//                    Uri.Builder builder = new Uri.Builder()
//                            .appendQueryParameter("token", params[0])
//                            .appendQueryParameter("userid", params[1]);
//                    String encodedParams = builder.build().getEncodedQuery();
//
//                    Log.e("Join game", encodedParams);
//
//                    return RESTConnector.POSTQuery(encodedParams, params[2]);
//
//
//                }catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//
//
//                return null;
//            }
//
//            @Override
//            protected void onPreExecute() {
//                progressDialog = ProgressDialog.show(context,
//                        "Please wait",
//                        "Attempting to join room");
//
//            }
//
//            @Override
//            protected void onPostExecute(JSONObject jsonObject) {
//                progressDialog.dismiss();
//
//                System.out.println("OBJECT " + jsonObject);
//
//
//
//            }
//
//
//        }.execute(User.token, User.id, "/multiplayer/room/" + roomID + "/join");
//    }


}