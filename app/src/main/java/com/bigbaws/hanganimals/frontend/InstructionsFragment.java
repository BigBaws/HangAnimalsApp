package com.bigbaws.hanganimals.frontend;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bigbaws.hanganimals.R;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class InstructionsFragment extends Fragment implements View.OnClickListener {

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.multiplayer_fragment, container, false);
        return v;
    }

    @Override
    public void onClick(View v) {

    }
}
