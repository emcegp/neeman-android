package com.intellum.neeman;

import android.webkit.WebViewClient;

/**
 * Created by arsent on 2016-06-02.
 */
public interface IWebFragment {
    void loadContent();
    void addWebFragment(String url);
    WebViewClient getWebClient();
    String getUrl();
}
