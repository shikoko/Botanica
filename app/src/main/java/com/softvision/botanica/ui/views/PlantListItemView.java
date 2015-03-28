package com.softvision.botanica.ui.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.softvision.botanica.R;
import com.softvision.botanica.common.pojo.nested.PlantPOJO;
import com.softvision.botanica.ui.views.custom.TileImageView;

/**
 * Created by lorand.krucz on 3/28/2015.
 */
public class PlantListItemView extends LinearLayout {
    private TileImageView image;
    private TextView title;
    private TextView text;

    public PlantListItemView(Context context) {
        super(context);
        init(context);
    }

    public PlantListItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlantListItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.plant_list_item_view, this, true);
        image = (TileImageView)findViewById(R.id.image);
        title = (TextView)findViewById(R.id.title);
        text = (TextView)findViewById(R.id.text);
    }

    public void setData(PlantPOJO item) {
        image.reset();
        image.setUrl(item.getPicture());
        title.setText(getResources().getString(R.string.title_format, item.getName(), item.getBothanicalName()));
        text.setText(item.getDescription());
    }
}
