package com.softvision.botanica.ui.utils;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.softvision.botanica.BotanicaApplication;
import com.softvision.botanica.ui.BotanicaActivity;
import com.softvision.botanica.ui.fragments.BotanicaAckDialogFragment;

import java.io.File;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 2/20/13
 * Time: 6:20 PM
 */
public class UiUtils {
    public static final int DEFAULT_BUFFER_SIZE = 128 * 1024;

    /**
     * The method will be used to hide the virtual keyboard
     */
    public static void hideVirtualKeyboard(Context context, View view) {
        if (context != null && view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Open a link with Android OS browser
     *
     * @param url url to open
     */
    public static void openLinkByDefaultBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }


    /**
     * Show an acknowledge dialog
     *
     * @param activity       the activity that will host the dialog
     * @param ackTitle       the title of the dialog
     * @param ackMessage     the message to be acknowledged shown in the dialog
     */
    synchronized public static void showAcknowledgeDialog(BotanicaActivity activity, String ackTitle, String ackMessage) {
        if (activity == null || !activity.isVisible()) return;
        //remove any currently showing dialog, so make our own transaction and take care of that here
        FragmentManager fragmentManager = activity.getFragmentManager();

        if (BotanicaAckDialogFragment.isShown() && BotanicaAckDialogFragment.getTextAcknowledge().equals(ackMessage)) {
            return;
        } else {
            //setup the dialog arguments
            Bundle args = new Bundle();
            args.putString(BotanicaAckDialogFragment.TITLE_ACK_BUNDLE_KEY, ackTitle);
            args.putString(BotanicaAckDialogFragment.TEXT_ACK_BUNDLE_KEY, ackMessage);

            //create and show the dialog
            BotanicaAckDialogFragment dialogToBeAdded = new BotanicaAckDialogFragment();
            dialogToBeAdded.setArguments(args);

            dialogToBeAdded.show(fragmentManager, BotanicaAckDialogFragment.class.getCanonicalName());
        }
    }

    public static void scrollParentToChild(ViewGroup parent, View child) {
        int scrollPos = UiUtils.getYChildOffsetInParent(parent, child);
        parent.scrollTo(parent.getScrollX(), scrollPos);
    }

    public static int getXChildOffsetInParent(ViewGroup parent, View child) {
        return getChildOffsetInParent(parent, child)[0];
    }

    public static int getYChildOffsetInParent(ViewGroup parent, View child) {
        return getChildOffsetInParent(parent, child)[1];
    }

    public static int[] getChildOffsetInParent(ViewGroup parent, View child) {
        int[] parentLocation = new int[2];
        int[] childLocation = new int[2];
        parent.getLocationOnScreen(parentLocation);
        child.getLocationOnScreen(childLocation);
        return new int[]{childLocation[0] - parentLocation[0], childLocation[1] - parentLocation[1]};
    }

    public static int getXChildOffsetOnScreen(View child) {
        int[] childLocation = new int[2];
        child.getLocationOnScreen(childLocation);
        return childLocation[0];
    }

    public static int getYChildOffsetOnScreen(View child) {
        int[] childLocation = new int[2];
        child.getLocationOnScreen(childLocation);
        return childLocation[1];
    }

    public static Bitmap resizeImage(Bitmap image, int imageWidth, int imageHeight) {
        float horizontalRatio = (float) (imageWidth) / image.getWidth();
        float verticalRatio = (float) (imageHeight) / image.getHeight();
        float ratio = Math.max(horizontalRatio, verticalRatio);

        return Bitmap.createScaledBitmap(image, Math.round(image.getWidth() * ratio), Math.round(image.getHeight() * ratio), true);
    }

    public static String getExtension(String fileName) {
        if (!TextUtils.isEmpty(fileName)) {
            int lastDotIndex = fileName.lastIndexOf('.');
            if (lastDotIndex != -1) {
                return fileName.substring(lastDotIndex + 1);
            } else {
                return null;
            }
        }
        return null;
    }

    public static File getFileDir(Context context, String attachmentType, Integer messageKey, Integer frameKey) {
        String partialPath = attachmentType;
        if (attachmentType.equals("Download")) {
            partialPath += File.separator + messageKey + File.separator + frameKey;
        }
        File dir = new File(context.getFilesDir(), partialPath);
        dir.mkdirs();
        return dir;
    }

    public static String minsHoursDays(int mins) {
        int hours;
        if (mins % 60 == 0) {
            hours = mins / 60;
            if (hours % 24 == 0) {
                return hours / 24 + " days";
            } else {
                return hours + " hours";
            }
        } else {
            return mins + " minutes";
        }
    }

    public static int stringToMins(String str) {
        if (str.contains("minutes")) {
            return Integer.parseInt(str.substring(0, str.indexOf(" ")));
        } else {
            int mins = (str.contains("hour") ? 60 : 60 * 24);
            return mins * Integer.parseInt(str.substring(0, str.indexOf(" ")));
        }
    }

    public static int getPx(int dp) {
        return (int) (dp * BotanicaApplication.getContext().getResources().getDisplayMetrics().density);
    }
}
