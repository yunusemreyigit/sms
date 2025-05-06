package org.example.swe304.Exceptions;

import lombok.*;

import java.util.Date;


@Data
public class APIErrors<T> {

    private String errorId;
    private Date errorTime;
    private T errors;


    public String getErrorId() {
        return errorId;
    }

    public void setErrorId(String errorId) {
        this.errorId = errorId;
    }

    public Date getErrorTime() {
        return errorTime;
    }

    public void setErrorTime(Date errorTime) {
        this.errorTime = errorTime;
    }

    public T getErrors() {
        return errors;
    }

    public void setErrors(T errors) {
        this.errors = errors;
    }

    public APIErrors(Date errorTime, T errors, String id) {
        this.errorTime = errorTime;
        this.errors = errors;
        this.errorId = id;
    }

    @Override
    public String toString() {
        return "APIErrors{" +
                "id='" + errorId + '\'' +
                ", errorTime=" + errorTime +
                ", errors=" + errors +
                '}';
    }

    public APIErrors() {}



}
