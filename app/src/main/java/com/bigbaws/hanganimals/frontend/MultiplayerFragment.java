package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.bigbaws.hanganimals.R;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class MultiplayerFragment extends Fragment implements View.OnClickListener {

    private Button begin;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.multiplayer_fragment, container, false);

        /* Shared Preferences */
        SharedPreferences shared = this.getActivity().getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");
        System.out.println(" ********** Animal "+animal);

        /* Layout */

        return v;
    }

    @Override
    public void onClick(View view) {

    }
}
