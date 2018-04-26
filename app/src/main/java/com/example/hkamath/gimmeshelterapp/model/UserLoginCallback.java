package com.example.hkamath.gimmeshelterapp.model;

/**
 * Created by crsch on 2/25/2018.
 */

public interface UserLoginCallback {
    void onPostExecute(final Boolean success, String error);
    void onPostExecute(final Boolean success, int error);

}
