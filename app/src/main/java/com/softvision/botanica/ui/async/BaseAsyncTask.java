package com.softvision.botanica.ui.async;

import android.os.AsyncTask;

import com.softvision.botanica.common.err.ApiException;
import com.softvision.botanica.common.pojo.constants.ErrorCode;
import com.softvision.botanica.ui.BotanicaActivity;

public abstract class BaseAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
    private ErrorCode mErrorCode = null;

    @Override
    protected Result doInBackground(Params... params) {
        try {
            return executeAsyncOperation(params);
        } catch (ApiException e) {
            mErrorCode = e.getErrorCode();
        }

        return null;
    }

    public abstract Result executeAsyncOperation(Params... params) throws ApiException;

    @Override
    protected void onPostExecute(Result r) {
        if(!isSuccess()) {
            BotanicaActivity activity = BotanicaActivity.getCurrentActivity();
            if(activity != null) {
                activity.handleCommonErrorCodes(mErrorCode);
            }
        }
    }

    public ErrorCode getLastError() {
        return mErrorCode;
    }

    public boolean isSuccess() {
        return mErrorCode == null;
    }
}
