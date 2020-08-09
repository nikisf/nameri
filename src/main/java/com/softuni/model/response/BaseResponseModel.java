package com.softuni.model.response;

public abstract class BaseResponseModel {
    private String id;

    public BaseResponseModel() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
