package com.bigbaws.hanganimals.frontend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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
public class ChangeAnimalActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private ImageView image_animal;
    private Button change_animal_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.change_animal_activity);

    /* Shared Preferences */
        SharedPreferences shared = ChangeAnimalActivity.this.getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");

        /* Layout */
        image_animal = (ImageView) findViewById(R.id.change_animal_image);
        change_animal_save = (Button) findViewById(R.id.change_animal_btn_save);
        change_animal_save.setOnClickListener(this);

    /* Spinner and click listener */
        Spinner spinner = (Spinner) findViewById(R.id.change_animal_spinner);
        spinner.setOnItemSelectedListener(this);

    /* Spinner Drop down elements */
        List<String> animals = new ArrayList<String>();
        animals.add("Sheep-White");
        animals.add("Sheep-Pink");
        animals.add("Bunny-Blue");
        animals.add("Dragon-Green");

    /* Creating adapter for spinner */
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(ChangeAnimalActivity.this, android.R.layout.simple_spinner_item, animals);

        /* Drop down layout style - list view with radio button */
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        /* attaching data adapter to spinner */
        spinner.setAdapter(dataAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        /* Shared Preferences */
        SharedPreferences shared = getSharedPreferences("TempAnimal", Context.MODE_PRIVATE);
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
            SharedPreferences temp = getSharedPreferences("TempAnimal", Context.MODE_PRIVATE);
            String tempanimal = temp.getString("TempAnimal", "");

            if (!tempanimal.isEmpty()) {
                SharedPreferences shared = getSharedPreferences("Animal", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();
                editor.putString("Animal", tempanimal);
                editor.commit();
            }
            Intent intent = new Intent(ChangeAnimalActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }
    }
}
