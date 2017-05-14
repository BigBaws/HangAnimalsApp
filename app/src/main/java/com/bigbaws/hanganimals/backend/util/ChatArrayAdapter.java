package com.bigbaws.hanganimals.backend.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Silas on 14-05-2017.
 */

public class ChatArrayAdapter extends BaseAdapter implements ListAdapter {

    private CircleImageView chat_img;
    private TextView chat_msg, chat_name;
    private List<String> chatMessageList = new ArrayList<String>();
    private Context context;
    private int textViewResourceId;



    public ChatArrayAdapter(Context context, ArrayList<String> chatMessageList ) {
        this.chatMessageList = chatMessageList;
        this.context = context;
    }

    public int getCount() {
        return this.chatMessageList.size();
    }

    @Override
    public Object getItem(int pos) {
        return chatMessageList.get(pos);
    }


    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.chat_msg_item, null);
        }


        /* Handle assignment item in the list */
        chat_msg = (TextView) view.findViewById(R.id.chat_msg_txt);
        chat_img = (CircleImageView) view.findViewById(R.id.chat_image);



        chat_msg.setText(chatMessageList.get(position));


        return view;
    }



}