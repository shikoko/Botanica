package com.softvision.botanica.bl;

import android.content.Context;

import com.softvision.botanica.bl.api.ApiManager;
import com.softvision.botanica.bl.api.http.HttpApiCallSubject;
import com.softvision.botanica.bl.persist.PersistantUtils;
import com.softvision.botanica.common.err.ApiException;
import com.softvision.botanica.common.log.Log;
import com.softvision.botanica.common.pojo.constants.ErrorCode;
import com.softvision.botanica.common.pojo.in.InfoInputPOJO;
import com.softvision.botanica.common.pojo.out.InfoOutputPOJO;
import com.softvision.botanica.common.util.NetworkConnectivity;


/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 4/30/13
 * Time: 12:35 PM
 * Handles communication between UI and ApiManager
 */
public class BusinessLogic implements BlFacade {
    private static final String TAG = "BusinessLogic";
    private Context mContext;

    private static BusinessLogic instance = new BusinessLogic();

    private BusinessLogic() {
        ApiManager.getInstance().setApiCallSubject(new HttpApiCallSubject());
    }

    private static BusinessLogic getInstance() {
        return instance;
    }

    public static BlFacade getFacade() {
        return getInstance();
    }

    /**
     * Inject the context to be used by the business logic
     *
     * @param context - application context
     */
    @Override
    public void setContext(Context context) {
        mContext = context;
    }

    //*****************************************************************************************API STUFF

    private void checkForNetwork() throws ApiException {
        if (!NetworkConnectivity.isNetworkAvailable(mContext)) {
            throw new ApiException(ErrorCode.NO_NETWORK);
        }
    }

    @Override
    public InfoOutputPOJO info(String queryString) throws ApiException {
        Log.i(TAG, "OP info(" + queryString + ")");
        checkForNetwork();

        InfoInputPOJO infoInputPOJO = new InfoInputPOJO(queryString);
        return ApiManager.getInstance().info(infoInputPOJO);

    }

    //*****************************************************************************************MISCELLANEOUS STUFF

    @Override
    public String getUserEmail() {
        return PersistantUtils.getUserEmail(mContext);
    }

    @Override
    public void setUserEmail(String email) {
        PersistantUtils.setLoginInfo(mContext, email);
    }

    @Override
    public boolean isLoggedIn() {
        return PersistantUtils.isLoggedIn(mContext);
    }
}