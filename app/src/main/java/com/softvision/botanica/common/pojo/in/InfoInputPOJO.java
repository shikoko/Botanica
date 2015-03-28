package com.softvision.botanica.common.pojo.in;

/**
 * Created with IntelliJ IDEA.
 * User: lorand.krucz
 * Date: 4/30/13
 * Time: 3:10 PM
 * Obj containing SignOn data sent to server
 */
public class InfoInputPOJO {
    private String queryString;

    public InfoInputPOJO(String queryString) {
        this.queryString = queryString;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }
}
