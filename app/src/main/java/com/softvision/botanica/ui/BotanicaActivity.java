package com.softvision.botanica.ui;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;

import com.softvision.botanica.R;
import com.softvision.botanica.bl.persist.PersistantUtils;
import com.softvision.botanica.common.pojo.constants.ErrorCode;
import com.softvision.botanica.ui.util.UiUtils;

public abstract class BotanicaActivity extends ActionBarActivity {
    private boolean mVisible = false;
    private static BotanicaActivity currentActivity;

    public static BotanicaActivity getCurrentActivity() {
        return currentActivity;
    }

    @Override
    @SuppressWarnings("unchecked")
    protected void onResume() {
        super.onResume();
        mVisible = true;
        currentActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVisible = false;
    }

    @Override
    protected void onDestroy() {
        if (currentActivity == this) {
            currentActivity = null;
        }
        super.onDestroy();
    }

    public void handleCommonErrorCodes(ErrorCode errorCode) {
        PersistantUtils.setLastErrorCode(this, errorCode.getIndicator());
        switch (errorCode) {
            case NO_REPLY:
            case NO_NETWORK:
            case CONNECTION_TIMED_OUT:
                if (mVisible) {
                    UiUtils.showAcknowledgeDialog(BotanicaActivity.getCurrentActivity(), getString(R.string.device_has_no_internet_connectivity_title), getString(R.string.device_has_no_internet_connectivity_text));
                }
                break;
        }
    }

    public boolean isVisible() {
        return mVisible;
    }
}
