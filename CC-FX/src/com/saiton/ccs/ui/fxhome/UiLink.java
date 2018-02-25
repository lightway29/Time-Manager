package com.saiton.ccs.ui.fxhome;


public interface UiLink {
    /**
     * holds the callback
     */
    public static interface SimpleCallback {

        public void callback(FxHome home);
    }
    
    public String getUiName();

    public String getUiIconResPath();

    public SimpleCallback getCallback();
}
