package com.sephora.happyshop.data.source;

public interface LoadDataCallback<T> {
    void onDataLoaded(T data);
    void onDataNotAvailable();
}
