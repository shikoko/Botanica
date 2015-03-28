package com.softvision.botanica.common.pojo.in;

public class QueryInputPOJO {
    private double latitude;
    private double longitude;
    private String queryString;
    private int limit;

    public QueryInputPOJO(double latitude, double longitude, String queryString, int limit) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.queryString = queryString;
        this.limit = limit;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
