package com.breezometer.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.breezometer.R;

import java.util.ArrayList;

/**
 * Created by Nadzer
 * 03/07/2016.
 */
public class ListAdapter extends BaseAdapter {
    private ArrayList<String> arrayList;
    private LayoutInflater layoutInflater;
    private String color;

    public ListAdapter(Context context, ArrayList<String> arrayList, String color) {
        this.arrayList = arrayList;
        this.layoutInflater = LayoutInflater.from(context);
        this.color = color;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Variables Declaration
        View view = layoutInflater.inflate(R.layout.listview_layout, null, false);
        TextView titre = (TextView) view.findViewById(R.id.list_text);
        ImageView image = (ImageView) view.findViewById(R.id.list_image);

        // Variables instantiation
        titre.setText(arrayList.get(position));
        image.setBackgroundColor(Color.parseColor(color));
        if (position == 0 || position == 3) image.setImageResource(R.drawable.home);
        if (position == 1) image.setImageResource(R.drawable.heart);
        if (position == 2) image.setImageResource(R.drawable.sport);

        image.setMaxHeight(25);
        image.setMaxWidth(25);
        return view;
    }
}
