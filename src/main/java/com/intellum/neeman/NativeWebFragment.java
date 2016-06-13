package com.intellum.neeman;

import android.util.Log;

public abstract class NativeWebFragment extends NeemanWebFragment {

    public static final String TAG = "DefaultWebFragment";

    public void loadContent() {
        String url = getUrl();
        Log.d(TAG, "Loading : " + url);
        vWebView.loadUrl(url);

    }
}
