package com.intellum.neeman;

import android.os.Bundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

/**
 * Created by arsent on 2016-06-09.
 */
public class JsoupWebFragment extends NeemanWebFragment {
    private Document mDocument;

    public static JsoupWebFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        JsoupWebFragment fragment = new JsoupWebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void loadContent() {
        try {
            mDocument = Jsoup.connect(getUrl()).get();
            Element head = mDocument.head();
            head.append(getCSSContent());
            vWebView.loadData(mDocument.html(), "text/html; charset=utf-8", "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Document getDocument() {
        return mDocument;
    }

    @Override
    public void addWebFragment(String url) {
//        JsoupWebFragment webFragment = JsoupWebFragment.newInstance(url);
//        addFragment(webFragment);
    }

    @Override
    public NeemanWebViewClient getWebClient() {
        return null;
    }
}
