package com.intellum.neeman;

import android.webkit.WebViewClient;

/**
 * Created by arsent on 2016-06-02.
 */
public class NeemanWebViewClient extends WebViewClient{
    private WebFragment mWebFragment;

    public NeemanWebViewClient(WebFragment webFragment){
        this.mWebFragment = webFragment;
    }

    public WebFragment getWebFragment() {
        return mWebFragment;
    }
}
