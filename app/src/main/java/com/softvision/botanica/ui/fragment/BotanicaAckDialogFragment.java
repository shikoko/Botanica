package com.softvision.botanica.ui.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.ContextThemeWrapper;

import com.softvision.botanica.R;

public class BotanicaAckDialogFragment extends DialogFragment {
    public static final String TITLE_ACK_BUNDLE_KEY = "title_ack_bundle_key";
    public static final String TEXT_ACK_BUNDLE_KEY = "text_ack_bundle_key";

    private static String titleAcknowledge;
    private static String textAcknowledge = "";
    private static boolean isShown;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        extractArgs(savedInstanceState);
        // retain dialog's instance ('textAcknowledge' reference will not be lost) upon orientation change
        setRetainInstance(true);
    }

    public static String getTextAcknowledge() {
        return textAcknowledge;
    }

    public static boolean isShown() {
        return isShown;
    }

    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        extractArgs(args);
    }

    private void extractArgs(Bundle args) {
        if (args != null) {
            titleAcknowledge = args.containsKey(TITLE_ACK_BUNDLE_KEY) ? args.getString(TITLE_ACK_BUNDLE_KEY) : titleAcknowledge;
            textAcknowledge = args.containsKey(TEXT_ACK_BUNDLE_KEY) ? args.getString(TEXT_ACK_BUNDLE_KEY) : textAcknowledge;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(TITLE_ACK_BUNDLE_KEY, titleAcknowledge);
        outState.putString(TEXT_ACK_BUNDLE_KEY, textAcknowledge);
    }

    @Override
    public Dialog onCreateDialog(Bundle args) {
        AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_DeviceDefault_Dialog));

        //set the built dialog to be non cancelable
        builder.setCancelable(false);

        if (!TextUtils.isEmpty(titleAcknowledge)) {
            builder.setTitle(titleAcknowledge);
        }

        //set the dialog text
        builder.setMessage(textAcknowledge);
        //set the positive button
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        isShown = true;
        super.show(manager, tag);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        isShown = false;
        textAcknowledge = "";
        super.onDismiss(dialog);
    }
}