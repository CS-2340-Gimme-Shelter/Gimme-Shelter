package com.example.hkamath.gimmeshelterapp.model;

import java.util.List;

/**
 * Created by crsch on 3/5/2018.
 */

public interface ShelterFetchCallback {
    void onFail();
    void sheltersFetched(List<Shelter> shelters);
}
