package com.doan.cv.Entity;

public class RestResponse<T> {
    private Integer statusCode;
    private String error;

    private Object message;
    private T data;

    public RestResponse() {
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public String getError() {
        return error;
    }

    public Object getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public void setData(T data) {
        this.data = data;
    }
}
