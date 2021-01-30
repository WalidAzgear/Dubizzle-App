package com.test.dubizzleapp.repository;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * This class the base class for the Data response.
 * it's contains ENUM of "SUCCESS" and "ERROR" for the API call statues
 * contains a generic type of data can take any type of data
 * contains message if we need to add message to UI
 */
public class Resource<T> {

    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    private Resource(@NonNull Status status, @Nullable T data,
                     @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(Status.SUCCESS, data, null);
    }

    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(Status.ERROR, data, msg);
    }

    public enum Status {SUCCESS, ERROR}
}