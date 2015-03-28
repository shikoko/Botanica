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
import com.softvision.botanica.ui.utils.AnimationUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by lorand.krucz on 3/28/2015.
 */
public class TileImageView extends RelativeLayout {
    private static final long ANIMATION_DURATION = 300;

    private static ImageLoaderThread imageLoaderThread;
    private ImageView imageView;
    private View progressBarContainer;
    private Runnable lastLoadRunnable;
    private Runnable lastResultRunnable;

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
        LayoutInflater.from(context).inflate(R.layout.image_tile_view, this, true);

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
        if(lastLoadRunnable != null) {
            imageLoaderThread.handler.removeCallbacks(lastLoadRunnable);
        }
        if(lastResultRunnable != null) {
            BotanicaApplication.getMainHandler().removeCallbacks(lastResultRunnable);
        }
        lastLoadRunnable = new Runnable() {
            @Override
            public void run() {
                URL url = null;
                try {
                    url = new URL(imageHttpUrl);
                    final Bitmap bmp = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                    lastResultRunnable = new Runnable() {
                        @Override
                        public void run() {
                            setBitmap(bmp);
                        }
                    };
                    BotanicaApplication.getMainHandler().post(lastResultRunnable);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        imageLoaderThread.handler.post(lastLoadRunnable);
    }

    private void setBitmap(final Bitmap bmp) {
        AnimationUtils.create().rotateY(this, ANIMATION_DURATION, 0, 90).after(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bmp);
                imageView.setVisibility(VISIBLE);
                progressBarContainer.setVisibility(GONE);
                AnimationUtils.create().rotateY(TileImageView.this, ANIMATION_DURATION, 270, 360).start();
            }
        }).start();
    }

    public void reset() {
        imageView.setVisibility(GONE);
        progressBarContainer.setVisibility(VISIBLE);
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
