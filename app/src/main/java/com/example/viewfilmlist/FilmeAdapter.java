package com.example.viewfilmlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;

public class FilmeAdapter extends ArrayAdapter<Films> {
    private LayoutInflater inflater;
    private int layout;
    private ArrayList<Films> productList;

    FilmeAdapter(Context context, int resource, ArrayList<Films> filsms) {
        super(context, resource, filsms);
        this.productList = filsms;
        this.layout = resource;
        this.inflater = LayoutInflater.from(context);
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if(convertView==null){
            convertView = inflater.inflate(this.layout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Films film = productList.get(position);

        viewHolder.nameView.setText(film.getName());
        viewHolder.typeView.setText(film.gettype());
        viewHolder.rateView.setText(film.getRate());

        return convertView;
    }


    private class ViewHolder {
        final TextView nameView,typeView, rateView;
        ViewHolder(View view){
            nameView = view.findViewById(R.id.nameView);
            typeView = view.findViewById(R.id.typefilm);
            rateView = view.findViewById(R.id.ratef);
        }
    }
}
