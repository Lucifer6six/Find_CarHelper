package com.upbest.ioc;

import android.app.Activity;
import android.view.View;

/**
 * 本类作用: View的findViewById的辅助类
 * Created by kGY on 2017/8/25.
 * Email 18252032703@163.com
 * qq 827746955 mobile 18252032703
 * Thank you for watching my code
 *
 * @author kang gui yang
 */

public class ViewFinder {

    private Activity mActivity;
    private View mView;


    public ViewFinder(Activity activity) {
        this.mActivity = activity;
    }

    public ViewFinder(View view) {
        this.mView = view;
    }

    public View findViewById(int viewId) {
        return mActivity != null ? mActivity.findViewById(viewId) : mView.findViewById(viewId);
    }
}
