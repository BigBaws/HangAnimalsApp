package com.bigbaws.hanganimals.backend.util;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bigbaws.hanganimals.R;

import java.util.ArrayList;

/**
 * Created by Silas on 26-04-2017.
 */

public class ChangeAnimalCustomAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] animals;
    private final Integer[] imgid;

    public ChangeAnimalCustomAdapter(Activity context, String[] animals, Integer[] imgid) {
        super(context, R.layout.animal_list, animals);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.animals=animals;
        this.imgid=imgid;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.animal_list, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.animals_name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);

        txtTitle.setText(animals[position]);
        imageView.setImageResource(imgid[position]);


        //TODO Lav logik der tester på om et dyr er købt eller ej.



//        if(isBought) {
//            imageView.setImageResource(imgid[position]);
//        }else {
//        Drawable[] layers = new Drawable[2];
//        layers[0] = ContextCompat.getDrawable(getContext(), imgid[position]);
//        layers[1] = ContextCompat.getDrawable(getContext(), R.drawable.buy_now);
//        LayerDrawable layerDrawable = new LayerDrawable(layers);
//        imageView.setImageDrawable(layerDrawable);

//        }

        return rowView;

    }

}
