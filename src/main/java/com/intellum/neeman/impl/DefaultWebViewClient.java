package com.intellum.neeman.impl;

import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;

import com.intellum.neeman.NeemanWebFragment;
import com.intellum.neeman.NeemanWebViewClient;
import com.intellum.neeman.cache.CacheUtils;

/**
 * Created by arsent on 2016-06-02.
 */
public class DefaultWebViewClient extends NeemanWebViewClient {

    public static final String TAG = "WebViewClient";
    //TODO: abstract out
    public static final String DEFAULT_CSS_FILE = "main.css";
    private long startTime;

    public DefaultWebViewClient(NeemanWebFragment webFragment) {
        super(webFragment);
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        boolean shouldOverride = view.getOriginalUrl() != null && !view.getOriginalUrl().equals(url);
        if (shouldOverride) {
            getWebFragment().addWebFragment(url);
        }
        return shouldOverride;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        Log.d(TAG, "onPageFinished: " + url);
        Log.d(TAG, "PAGE LOAD: " + (System.currentTimeMillis() - startTime));
        injectCss(view, DEFAULT_CSS_FILE);
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        Log.d(TAG, "onPageStarted: " + url);
        startTime = System.currentTimeMillis();
        notifyStarted();
    }

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
        Log.d(TAG, "Loading Resource: " + url);
    }

    @Override
    public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
        WebResourceResponse response = CacheUtils.getInstance().load(request.getUrl());
        return response;
    }

    private void notifyStarted(){
        if (getListener() != null){
            getListener().onPageStarted();
        }
    }

    private void notifyFinished(){
        if (getListener() != null){
            getListener().onPageFinished();
        }
    }

    protected void injectCss(final WebView webView, String cssFileName) {
        String cssContent = getWebFragment().getCSSContent(cssFileName);
        String encoded = Base64.encodeToString(cssContent.getBytes(), Base64.NO_WRAP);

        String injectableCssContent = "javascript:(function() {" +
                "var parent = document.getElementsByTagName('head').item(0);" +
                "var style = document.createElement('style');" +
                "style.type = 'text/css';" +
                // Tell the browser to BASE64-decode the string into your script !!!
                "style.innerHTML = window.atob('" + encoded + "');" +
                "parent.appendChild(style)" +
                "})()";

        webView.evaluateJavascript(injectableCssContent, new ValueCallback<String>() {
            @Override
            public void onReceiveValue(String s) {
                webView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        notifyFinished();
                    }
                }, 1000);
            }
        });
    }


}
