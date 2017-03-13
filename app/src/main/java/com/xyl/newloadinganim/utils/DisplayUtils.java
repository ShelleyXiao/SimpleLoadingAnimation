package com.xyl.newloadinganim.utils;

import com.xyl.newloadinganim.App;

/**
 * User: ShaudXiao
 * Date: 2017-03-13
 * Time: 14:47
 * Company: zx
 * Description:
 * FIXME
 */


public class DisplayUtils {

    public static int dip2px(float value) {
        final float scal = App.mApp.getResources().getDisplayMetrics().density;
        return (int) (value * scal + 0.5f);
    }

}
