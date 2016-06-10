package com.intellum.neeman;

import android.webkit.WebViewClient;

/**
 * Created by arsent on 2016-06-02.
 */
public class NeemanWebViewClient extends WebViewClient{
    private NeemanWebFragment mWebFragment;
    private NeemanPageListener mListener;

    public NeemanWebViewClient(NeemanWebFragment webFragment){
        this.mWebFragment = webFragment;
    }

    public NeemanWebFragment getWebFragment() {
        return mWebFragment;
    }

    public void setListener(NeemanPageListener mListener) {
        this.mListener = mListener;
    }

    public NeemanPageListener getListener() {
        return mListener;
    }
}
