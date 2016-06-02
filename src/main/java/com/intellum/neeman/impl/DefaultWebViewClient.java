package com.intellum.neeman.impl;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.intellum.neeman.NeemanWebViewClient;
import com.intellum.neeman.WebFragment;

import java.io.InputStream;

/**
 * Created by arsent on 2016-06-02.
 */
public class DefaultWebViewClient extends NeemanWebViewClient {
    public static final String TAG = "WebViewClient";
    //TODO: abstract out
    public static final String DEFAULT_CSS_FILE = "main.css";

    public DefaultWebViewClient(WebFragment webFragment) {
        super(webFragment);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        boolean shouldOverride = !view.getOriginalUrl().equals(url);
        if (shouldOverride) {
            getWebFragment().addWebFragment(url);
        }
        return shouldOverride;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.d(TAG, "onPageFinished: " + url);
        injectCss(view, DEFAULT_CSS_FILE);
        view.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d(TAG, "onPageStarted: " + url);
        view.setVisibility(View.GONE);
    }

    protected void injectCss(WebView webView, String cssFileName) {
        webView.loadUrl(getCSSContent(cssFileName));
    }

    protected String getCSSContent(String cssFileName) {
        try {
            InputStream inputStream = getWebFragment().getResources().getAssets().open(cssFileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            String encoded = Base64.encodeToString(buffer, Base64.NO_WRAP);
            return "javascript:(function() {" +
                    "var parent = document.getElementsByTagName('head').item(0);" +
                    "var style = document.createElement('style');" +
                    "style.type = 'text/css';" +
                    // Tell the browser to BASE64-decode the string into your script !!!
                    "style.innerHTML = window.atob('" + encoded + "');" +
                    "parent.appendChild(style)" +
                    "})()";
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
