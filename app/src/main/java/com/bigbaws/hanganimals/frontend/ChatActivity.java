package com.bigbaws.hanganimals.frontend;

import android.database.DataSetObserver;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.dao.RESTConnector;
import com.bigbaws.hanganimals.backend.exceptions.DAOException;
import com.bigbaws.hanganimals.backend.user.User;
import com.bigbaws.hanganimals.backend.util.ChatArrayAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Silas on 13-May-17.
 */

public class ChatActivity extends AppCompatActivity {
    private static final String TAG = "ChatActivity";

    private ChatArrayAdapter chatArrayAdapter;
    private ArrayList<String> chatMessages = new ArrayList<>();
    private ListView msgView;
    private EditText chatText;
    private Button buttonSend;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        /* Method listens for íncoming messages */
        getMessagesAsync();


        /* Views */
        buttonSend = (Button) findViewById(R.id.send);
        msgView = (ListView) findViewById(R.id.msgview);
        chatText = (EditText) findViewById(R.id.msg);


        /* Messages list  */
        chatArrayAdapter = new ChatArrayAdapter(this, chatMessages);
        msgView.setAdapter(chatArrayAdapter);




        /* Send message  */
        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                if(chatText.getText().toString().equals("")) {
                    Toast.makeText(ChatActivity.this, "You need to type something ...", Toast.LENGTH_SHORT).show();
                } else {
                    String newMessage = chatText.getText().toString();
                    sendChatMessageAsync(newMessage);

                    chatArrayAdapter.notifyDataSetChanged();

                    chatText.setText("");
                }
            }
        });

        msgView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        msgView.setAdapter(chatArrayAdapter);

        //to scroll the list view to bottom on data change
        chatArrayAdapter.registerDataSetObserver(new DataSetObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                msgView.setSelection(chatArrayAdapter.getCount() - 1);
            }
        });
    }

    /* Async call send message */
    private void sendChatMessageAsync(final String message) {
        new AsyncTask<String, Void, JSONObject>() {
            @Override
            protected JSONObject doInBackground(String... params) {

                try {
                    Uri.Builder builder = new Uri.Builder()
                            .appendQueryParameter("token", User.token)
                            .appendQueryParameter("userid", User.id)
                            .appendQueryParameter("message", message);
                    String encodedParams = builder.build().getEncodedQuery();

                    Log.e("Send Chat Msg", encodedParams);

                    return RESTConnector.POSTQuery(encodedParams, params[0]);


                }catch (Exception e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                System.out.println("SEND MESSAGE RESPONSE: " + jsonObject);
            }

        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,"/chat/send");

    }

    /* async call get messages */
    public void getMessagesAsync() {
        new AsyncTask<String, Void, JSONObject>() {

            @Override
            protected JSONObject doInBackground(String... params) {

                try {
                    JSONObject json = RESTConnector.GETQuery("/chat/listen/" + User.id);
                    System.out.println("JSON " + json);
                    return json;

                } catch (DAOException e) {
                    e.printStackTrace();
                }
                return null;
            }


            @Override
            protected void onPostExecute(JSONObject jsonObject) {

                chatMessages.add(RESTConnector.GETResponseString);
                chatArrayAdapter.notifyDataSetChanged();

                getMessagesAsync();
                System.out.println("GET MESSAGES: " + jsonObject);

            }


        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.out.println("on back pressed");
        this.finish();
    }
}
