package com.example.lab7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private Context applicationContext;
    private List<JsonModel> jsonModels;

    CustomAdapter(Context applicationContext, List<JsonModel> jsonModels) {
        this.applicationContext =applicationContext;
        this.jsonModels =jsonModels;

    }

    @Override
    public int getCount() {
        return jsonModels.size();
    }

    @Override
    public Object getItem(int i) {
        return jsonModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) applicationContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view =  layoutInflater.inflate(R.layout.row_layout,viewGroup,false);

        }

        TextView textView;
        textView= view.findViewById(R.id.textView);
        textView.setText(jsonModels.get(i).getName());

        return view;
    }
}
