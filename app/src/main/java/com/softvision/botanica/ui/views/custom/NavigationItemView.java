package com.softvision.botanica.ui.views.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.softvision.botanica.R;

public class NavigationItemView extends RelativeLayout {

    private ImageView icon;
    private TextView text;

    public NavigationItemView(Context context) {
        super(context);
        init(context);
    }

    public NavigationItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public NavigationItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public NavigationItemView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_navigation_list, this);
        icon = (ImageView) view.findViewById(R.id.icon_navigation);
        text = (TextView) view.findViewById(R.id.text_navigation);
    }

    public void setIcon(int resId) {
        icon.setBackground(getResources().getDrawable(resId));
    }

    public void setText(String info) {
        text.setText(info);
    }

}
