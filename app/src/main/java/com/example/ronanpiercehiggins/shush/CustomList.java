package com.example.ronanpiercehiggins.shush;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomList extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> web;
    private final ArrayList<Integer> imageId;

    private final ArrayList<String> ObjectId;


    public CustomList(Activity context, ArrayList<String> web, ArrayList<Integer> imageId, ArrayList<String> ObjectId) {

        super(context, R.layout.list_single, web);
        this.context = context;
        this.web = web;
        this.imageId = imageId;
        this.ObjectId = ObjectId;

    }

    /*public void updateList(ArrayList<String> newlist) {
        web.clear();
        web.addAll(newlist);
        this.notifyDataSetChanged();
    }*/

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.list_single, null, true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);
        TextView imageView = (TextView) rowView.findViewById(R.id.img);





        txtTitle.setText(web.get(position));

        imageView.setText(String.valueOf(imageId.get(position)));
        //imageView.setImageResource(imageId[position]);


        return rowView;

    }

    /*public void getPos(){

    }*/
}
