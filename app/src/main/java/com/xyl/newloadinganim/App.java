package com.xyl.newloadinganim;

import android.app.Application;
import android.content.Context;

/**
 * User: ShaudXiao
 * Date: 2017-03-13
 * Time: 14:48
 * Company: zx
 * Description:
 * FIXME
 */


public class App extends Application {

    public static Context mApp;

    @Override
    public void onCreate() {
        super.onCreate();

        mApp = this;
    }
}
