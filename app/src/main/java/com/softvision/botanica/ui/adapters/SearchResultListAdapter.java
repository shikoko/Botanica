package com.softvision.botanica.ui.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.softvision.botanica.common.pojo.nested.PlantPOJO;
import com.softvision.botanica.common.pojo.out.QueryOutputPOJO;
import com.softvision.botanica.ui.views.PlantListItemView;

/**
 * Created by lorand.krucz on 3/28/2015.
 */
public class SearchResultListAdapter extends BaseAdapter {
    private QueryOutputPOJO lastSearchResult;

    public void setLastSearchResult(QueryOutputPOJO lastSearchResult) {
        this.lastSearchResult = lastSearchResult;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return lastSearchResult == null ? 0 : lastSearchResult.getPlants().size();
    }

    @Override
    public Object getItem(int position) {
        return lastSearchResult.getPlants().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = new PlantListItemView(parent.getContext());
        }
        ((PlantListItemView)convertView).setData((PlantPOJO)getItem(position));

        return convertView;
    }
}
