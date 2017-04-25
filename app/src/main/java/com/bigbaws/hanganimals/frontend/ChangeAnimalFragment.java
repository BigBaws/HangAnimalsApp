package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class ChangeAnimalFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView image_animal;
    private Button change_animal_save;

    public View onCreateView(LayoutInflater i, ViewGroup container, Bundle savedInstanceState) {
        View v = i.inflate(R.layout.change_animal_fragment, container, false);

        /* Shared Preferences */
        SharedPreferences shared = this.getActivity().getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");

        /* Layout */
        image_animal = (ImageView) v.findViewById(R.id.change_animal_image);
        change_animal_save = (Button) v.findViewById(R.id.change_animal_btn_save);
        change_animal_save.setOnClickListener(this);

        /* Spinner and click listener */
        Spinner spinner = (Spinner) v.findViewById(R.id.change_animal_spinner);
        spinner.setOnItemSelectedListener(this);

        /* Spinner Drop down elements */
        List<String> animals = new ArrayList<String>();
        animals.add("Sheep-White");
        animals.add("Sheep-Pink");
        animals.add("Bunny-Blue");
        animals.add("Dragon-Green");

        /* Creating adapter for spinner */
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, animals);

        /* Drop down layout style - list view with radio button */
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        /* attaching data adapter to spinner */
        spinner.setAdapter(dataAdapter);

        return v;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /* Shared Preferences */
        SharedPreferences shared = this.getActivity().getSharedPreferences("TempAnimal", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();

        /* Get Spinner String */
        String item = parent.getItemAtPosition(position).toString();

        if (item == "Sheep-White") {
            image_animal.setImageResource(R.drawable.sheep_white);
            editor.putString("TempAnimal", "SheepWhite");
            editor.commit();
        } else if (item == "Sheep-Pink") {
            image_animal.setImageResource(R.drawable.sheep_pink);
            editor.putString("TempAnimal", "SheepPink");
            editor.commit();
        } else if (item == "Bunny-Blue") {
            image_animal.setImageResource(R.drawable.bunny_blue);
            editor.putString("TempAnimal", "BunnyBlue");
            editor.commit();
        } else if (item == "Dragon-Green") {
            image_animal.setImageResource(R.drawable.dragon_green);
            editor.putString("TempAnimal", "DragonGreen");
            editor.commit();
        }
        /* Showing selected spinner item */
        Toast.makeText(parent.getContext(), "Selected: " + item, Toast.LENGTH_LONG).show();
    }

    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onClick(View v) {
        if (change_animal_save.isPressed()) {
            /* Shared Preferences */
            SharedPreferences temp = this.getActivity().getSharedPreferences("TempAnimal", Context.MODE_PRIVATE);
            String tempanimal = temp.getString("TempAnimal", "");

            if (!tempanimal.isEmpty()) {
                SharedPreferences shared = this.getActivity().getSharedPreferences("Animal", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("Animal", tempanimal);
                editor.commit();
            }

            Fragment mmf = new MainMenuFragment();
            FragmentManager fm = getFragmentManager();
            fm.beginTransaction()
                    .add(R.id.fragment_body, mmf)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    .commit();
        }
    }
}
