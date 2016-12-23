package com.jackzc.www.jackintroslider;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 12/22/2016.
 */

public class IntroManager {

    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    Context context;

    public IntroManager(Context _context) {
        this.context = _context;
        preferences = context.getSharedPreferences("First", 0);
        editor = preferences.edit();
    }

    public void setFirst(boolean isFirst) {
        editor.putBoolean("Check", isFirst);
        editor.commit();
    }

    public boolean Check() {
        return preferences.getBoolean("Check", true);
    }

}
