package com.softvision.botanica.common.pojo;

public class ApiRequestPOJO<T> {
    private String email;
    private T payload;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
