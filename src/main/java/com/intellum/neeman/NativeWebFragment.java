package com.intellum.neeman;

import android.util.Log;

public abstract class NativeWebFragment extends WebFragment {

    public static final String TAG = "DefaultWebFragment";

    public void loadContent() {
        String url = getUrl();
        Log.d(TAG, "Loading : " + url);
        vWebView.loadUrl(url);
    }
}
