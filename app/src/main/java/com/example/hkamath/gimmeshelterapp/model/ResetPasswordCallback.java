package com.example.hkamath.gimmeshelterapp.model;

/**
 * Created by uday on 4/26/18.
 */

public interface ResetPasswordCallback {
    void onResetPassword(final Boolean success, String error);
    void onResetPassword(final Boolean success, int error);
}
