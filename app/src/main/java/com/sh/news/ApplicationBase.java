package com.sh.news;

import android.app.Application;
import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by shanaulhaque on 12/09/17.
 */

public class ApplicationBase extends Application {

    public static SharedPreferences mSharedPreferences;
    public static SharedPreferences.Editor editor;

    @Override
    public void onCreate() {
        super.onCreate();

        Log.i("Check ", "  onCreate" );
        mSharedPreferences = getSharedPreferences("MYPREF", MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static SharedPreferences getmSharedPreferences() {
        return mSharedPreferences;
    }

    public static void setmSharedPreferences(SharedPreferences mSharedPreferences) {
        ApplicationBase.mSharedPreferences = mSharedPreferences;
    }

    public static SharedPreferences.Editor getEditor() {
        return editor;
    }

    public static void setEditor(SharedPreferences.Editor editor) {
        ApplicationBase.editor = editor;
    }
}

