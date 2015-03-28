package com.softvision.botanica.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.softvision.botanica.R;
import com.softvision.botanica.ui.views.custom.NavigationItemView;

import java.util.ArrayList;
import java.util.List;

public class NavigationAdapter extends BaseAdapter {

    private Context context;

    private List<Integer> icons;
    private List<String> options;

    public NavigationAdapter(Context context) {
        this.options = new ArrayList<>();
        this.icons = new ArrayList<>();
        this.context = context;

        options.add("Search plants");
        options.add("Add plants");

        icons.add(R.drawable.icon_search);
        icons.add(R.drawable.icon_add);
    }

    @Override
    public int getCount() {
        return options.size();
    }

    @Override
    public Object getItem(int position) {
        return options.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = new NavigationItemView(context);
            ((NavigationItemView) convertView).setIcon(icons.get(position));
            ((NavigationItemView) convertView).setText(options.get(position));
        }
        return convertView;
    }
}
