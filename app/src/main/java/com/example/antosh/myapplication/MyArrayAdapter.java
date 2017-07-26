package com.example.antosh.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MyArrayAdapter extends ArrayAdapter<MyDataModel>{

    ArrayList<MyDataModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArrayAdapter(Context context, ArrayList<MyDataModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);

        vh.textViewName.setText(item.getName());
        vh.textViewSurname.setText(item.getSurname());
        vh.textViewCity.setText(item.getCity());
        vh.textViewStreet.setText(item.getStreet());
        Picasso.with(context).load(item.getImage()).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher).into(vh.imageView);

        return vh.rootView;
    }








    /**
     * ViewHolder class for layout
     */
    private static class ViewHolder {
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final TextView textViewName;
        public final TextView textViewSurname;
        public final TextView textViewStreet;
        public final TextView textViewCity;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, TextView textViewName,
                           TextView textViewSurname,TextView textViewCity,TextView textViewStreet) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.textViewSurname = textViewSurname;
            this.textViewStreet = textViewStreet;
            this.textViewCity = textViewCity;

        }
        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = (ImageView) rootView.findViewById(R.id.ivImage);
            TextView textViewName = (TextView) rootView.findViewById(R.id.textViewName);
            TextView textViewSurname = (TextView) rootView.findViewById(R.id.textViewSurname);
            TextView textViewCity = (TextView) rootView.findViewById(R.id.tvCity);
            TextView textViewStreet = (TextView) rootView.findViewById(R.id.tvStreet);
            return new ViewHolder(rootView, imageView, textViewName, textViewSurname,textViewCity,textViewStreet);
        }
    }
}