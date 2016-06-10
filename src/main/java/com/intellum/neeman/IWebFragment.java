package com.intellum.neeman;

/**
 * Created by arsent on 2016-06-02.
 */
public interface IWebFragment {
    void loadContent();
    void addWebFragment(String url);
    NeemanWebViewClient getWebClient();
    String getUrl();
}
