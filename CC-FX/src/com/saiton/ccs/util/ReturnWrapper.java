package com.saiton.ccs.util;

/**
 * Return Object Wrapper
 *
 * @param <T> Type Of Return
 */
public class ReturnWrapper<T> {
    private T returnObject;

    public ReturnWrapper() {
        this.returnObject = null;
    }

    public T getReturnObject() {
        return returnObject;
    }

    public void setReturnObject(T returnObject) {
        this.returnObject = returnObject;
    }
    
}
