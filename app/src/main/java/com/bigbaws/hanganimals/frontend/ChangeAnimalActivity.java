package com.bigbaws.hanganimals.frontend;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bigbaws.hanganimals.R;
import com.bigbaws.hanganimals.backend.util.CustomListAdapter;

/**
 * Created by BigBaws on 11-Jan-17.
 */
public class ChangeAnimalActivity extends ListActivity implements View.OnClickListener {

    private ImageView image_animal;
    private Button change_animal_save;
    private ListView animalsListView;
    String[] animals = {"White-sheep", "Pink-sheep", "Black-sheep", "Blue-bunny", "Green-dragon"};
    Integer[] imgId = {R.drawable.sheep_white, R.drawable.sheep_pink,  R.drawable.sheep_black, R.drawable.bunny_blue, R.drawable.dragon_green};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.change_animal_activity_new);

        /* Shared Preferences */
        SharedPreferences shared = ChangeAnimalActivity.this.getSharedPreferences("Animal", Context.MODE_PRIVATE);
        String animal = shared.getString("Animal", "");

        /* Layout */
        image_animal = (ImageView) findViewById(R.id.change_animal_image);
        change_animal_save = (Button) findViewById(R.id.change_animal_btn_save);
        change_animal_save.setOnClickListener(this);

        if(animal.equalsIgnoreCase("SheepWhite")) {
            image_animal.setImageResource(R.drawable.sheep_white);
        }else if(animal.equalsIgnoreCase("SheepPink")) {
            image_animal.setImageResource(R.drawable.sheep_pink);
        }else if(animal.equalsIgnoreCase("BunnyBlue")) {
            image_animal.setImageResource(R.drawable.bunny_blue);
        }else if(animal.equalsIgnoreCase("DragonGreen")) {
            image_animal.setImageResource(R.drawable.dragon_green);
        }else if(animal.equalsIgnoreCase("SheepBlack")) {
            image_animal.setImageResource(R.drawable.sheep_black);
        }


        // Måske kan listadapter klassen tilpasses ligesom animals klassen, og der kan tilføjes en boolean?
        CustomListAdapter adapter = new CustomListAdapter(this, animals, imgId);

        animalsListView = (ListView) findViewById(android.R.id.list);
        animalsListView.setAdapter(adapter);
        animalsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                 /* Shared Preferences */
                SharedPreferences shared = getSharedPreferences("TempAnimal", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = shared.edit();

                /* Get ListView String */
                String selectedItem = animals[+position];

                if (selectedItem == "White-sheep") {
                    image_animal.setImageResource(R.drawable.sheep_white);
                    editor.putString("TempAnimal", "SheepWhite");
                    editor.commit();
                } else if (selectedItem == "Pink-sheep") {
                    image_animal.setImageResource(R.drawable.sheep_pink);
                    editor.putString("TempAnimal", "SheepPink");
                    editor.commit();
                } else if (selectedItem == "Blue-bunny") {
                    image_animal.setImageResource(R.drawable.bunny_blue);
                    editor.putString("TempAnimal", "BunnyBlue");
                    editor.commit();
                } else if (selectedItem == "Green-dragon") {
                    image_animal.setImageResource(R.drawable.dragon_green);
                    editor.putString("TempAnimal", "DragonGreen");
                    editor.commit();
                }  else if (selectedItem == "Black-sheep") {
                    image_animal.setImageResource(R.drawable.sheep_black);
                    editor.putString("TempAnimal", "SheepBlack");
                    editor.commit();
                }
            }
        });
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
