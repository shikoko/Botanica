package com.softvision.botanica.bl;

import android.content.Context;

import com.softvision.botanica.common.err.ApiException;
import com.softvision.botanica.common.pojo.out.InfoOutputPOJO;
import com.softvision.botanica.common.pojo.out.QueryOutputPOJO;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 4/30/13
 * Time: 12:34 PM
 * Handles communication between UI and ApiManager
 */
public interface BlFacade {
    //*****************************************************************************************CONFIG/INJECT STUFF

    /**
     * Inject a context to be used by the Business logic where necessary
     *
     * @param context the context being injected
     */
    void setContext(Context context);

    //*****************************************************************************************API STUFF
    public InfoOutputPOJO info(String queryString) throws ApiException;
    public QueryOutputPOJO getPlantsList(String query, Integer limit) throws ApiException;

    //*****************************************************************************************MISCELLANEOUS STUFF

    public String getUserEmail();
    public void setUserEmail(String email);
    public boolean isLoggedIn();
}
