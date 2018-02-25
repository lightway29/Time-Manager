package com.saiton.ccs.base;

/**
 *
 * @author Saitonya
 */
public interface HomeCallback {

    public void setLoginAccess(LoginAccess access);
    public void startHomeUi();
    public void onLoginSuccess();
    public void startLoginUi();
}
