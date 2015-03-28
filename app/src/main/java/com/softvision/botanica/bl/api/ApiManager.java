package com.softvision.botanica.bl.api;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.softvision.botanica.BotanicaApplication;
import com.softvision.botanica.bl.misc.Settings;
import com.softvision.botanica.bl.persist.PersistantUtils;
import com.softvision.botanica.common.err.ApiException;
import com.softvision.botanica.common.log.Log;
import com.softvision.botanica.common.pojo.ApiReplyPOJO;
import com.softvision.botanica.common.pojo.ApiRequestPOJO;
import com.softvision.botanica.common.pojo.constants.ErrorCode;
import com.softvision.botanica.common.pojo.in.InfoInputPOJO;
import com.softvision.botanica.common.pojo.out.InfoOutputPOJO;

import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 4/30/13
 * Time: 12:02 PM
 */
public class ApiManager {
    private static final String TAG = "ApiManager";
    private ApiCallSubject mApiCallSubject;

    //CRITICAL !!! do not use mGson in methods directly (use it through aux methods)
    private final Gson mGson;
    private static ApiManager instance = new ApiManager();

    private ApiManager() {
        mGson = new GsonBuilder().setFieldNamingStrategy(FieldNamingPolicy.IDENTITY).create();
    }

    public static ApiManager getInstance() {
        return instance;
    }

    //************************************** AUX METHODS

    private String toJson(Object object) {
        return mGson.toJson(object);
    }

    private <T> T fromJson(String string, Type type) throws ApiException {
        try {
            return mGson.fromJson(string, type);

        } catch (JsonSyntaxException e) {
            throw new ApiException(e);
        }
    }

    //****************************************************************************************************** CONFIG METHODS

    /**
     * dependency inject the subject on which the API calls are made
     * this method can be used to mock out the API source for unit testing
     *
     * @param mApiCallSubject - the api subject to be injected
     */
    public void setApiCallSubject(ApiCallSubject mApiCallSubject) {
        this.mApiCallSubject = mApiCallSubject;
    }

    //****************************************************************************************************** API METHODS

    /**
     * perform an info call through the UI
     *
     * @param InfoInputPOJO the serializable input parameter of the sign in operation
     * @throws ApiException in case of some IO or server side error
     */
    public InfoOutputPOJO info(InfoInputPOJO InfoInputPOJO) throws ApiException {
        //create the request
        ApiRequestPOJO requestPOJO = createFilledRequestWrapper(InfoInputPOJO);

        //make the call
        String responseString;
        responseString = mApiCallSubject.getResponse(Settings.apiBaseUrl() + Settings.API_METHOD_SIGNON, toJson(requestPOJO));

        //parse response
        Type responseType = new TypeToken<ApiReplyPOJO<InfoOutputPOJO>>() {
        }.getType();
        ApiReplyPOJO<InfoOutputPOJO> replyPOJO = fromJson(responseString, responseType);

        //must call strip to retain SESSION ID
        handleCommonApiReply(replyPOJO);
        return replyPOJO.getPayload();
    }

    //****************************************************************************************************** AUX METHODS

    /**
     * Create a POJO wrapper for any request using the common request parts and a custom request object passed as a parameter
     *
     * @param request the specific request object being wrapped
     * @param <T>     the type of the request object being wrapped
     * @return the wrapper request object
     */
    private <T> ApiRequestPOJO createFilledRequestWrapper(T request) {
        ApiRequestPOJO<T> requestPojo = new ApiRequestPOJO<T>();
        requestPojo.setEmail(PersistantUtils.getUserEmail(BotanicaApplication.getContext()));
        requestPojo.setPayload(request);
        return requestPojo;
    }

    /**
     * this method handles the common operations for a reply object, detects api errors and retains session ID.
     *
     * @param replyPOJO the reply object received from API
     * @throws ApiException if error code is detected
     */
    private void handleCommonApiReply(ApiReplyPOJO replyPOJO) throws ApiException {
        if (replyPOJO == null) {
            Log.e(TAG, "Received null api reply");
            throw new ApiException((ErrorCode) null);
        }

        //get error code from response
        ErrorCode errorCode = replyPOJO.getError();
        PersistantUtils.setLastErrorCode(BotanicaApplication.getContext(), errorCode.getIndicator());
        //check the result error code, do any necessary session maintenance and throw an exception
        if (errorCode != ErrorCode.SUCCESS) {
            Log.e(TAG, "Received api error code " + errorCode);
            throw new ApiException(errorCode);
        }
    }
}