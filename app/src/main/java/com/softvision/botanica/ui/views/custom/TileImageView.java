package com.softvision.botanica.ui.views.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.softvision.botanica.BotanicaApplication;
import com.softvision.botanica.R;
import com.softvision.botanica.ui.utils.FlipAnimator;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lorand.krucz on 3/28/2015.
 */
public class TileImageView extends RelativeLayout {
    private static ImageLoaderThread imageLoaderThread;
    private ImageView imageView;
    private View progressBarContainer;

    public TileImageView(Context context) {
        super(context);
        init(context);
    }

    public TileImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TileImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R.layout.image_tile_view, this, false);

        imageView = (ImageView) findViewById(R.id.image_view);
        progressBarContainer = findViewById(R.id.progress_bar_container);

        if(imageLoaderThread == null) {
            synchronized (this.getClass()) {
                if(imageLoaderThread == null) {
                    imageLoaderThread = new ImageLoaderThread();
                    imageLoaderThread.start();
                }
            }
        }
    }

    public void setUrl(final String imageHttpUrl) {
        imageLoaderThread.handler.post(new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(imageHttpUrl);
                    final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    BotanicaApplication.getMainHandler().post(new Runnable() {
                        @Override
                        public void run() {
                            setBitmap(bmp);
                        }
                    });
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setBitmap(Bitmap bmp) {
        imageView.setImageBitmap(bmp);
        new FlipAnimator(progressBarContainer, imageView, progressBarContainer.getWidth() / 2, progressBarContainer.getHeight() / 2);
    }

    private static class ImageLoaderThread extends Thread {
        public Handler handler;

        @Override
        public void run() {
            Looper.prepare();
            handler = new Handler() {
                public void handleMessage(Message msg) {
                    //NOP
                }
            };
            Looper.loop();
        }
    }
}
