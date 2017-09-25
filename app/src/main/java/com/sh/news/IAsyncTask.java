package com.sh.news;

import android.app.Activity;

/**
 * Created by shanaulhaque on 12/09/17.
 */

public interface IAsyncTask {

    Activity getActivity();

    void postExecute();

}
