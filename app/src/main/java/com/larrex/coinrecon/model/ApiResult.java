package com.larrex.coinrecon.model;

public class ApiResult<T> {

    T result;

    public ApiResult(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}

