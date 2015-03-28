package com.softvision.botanica.bl.api;

/**
 * Used in ApiManager to dependency inject the subject on which the API calls are made
 */
public interface ApiCallSubject {
    public String getResponse(String url, String body);
}