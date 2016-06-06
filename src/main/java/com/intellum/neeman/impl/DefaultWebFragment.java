package com.intellum.neeman.impl;

import android.os.Bundle;

import com.intellum.neeman.NeemanWebViewClient;
import com.intellum.neeman.OkHttpWebFragment;

/**
 * Created by arsent on 2016-05-31.
 */
public class DefaultWebFragment extends OkHttpWebFragment {

    public static DefaultWebFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        DefaultWebFragment fragment = new DefaultWebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void addWebFragment(String url) {
        DefaultWebFragment webFragment = DefaultWebFragment.newInstance(url);
        addFragment(webFragment);
    }

    @Override
    public NeemanWebViewClient getWebClient() {
        return new DefaultWebViewClient(this);
    }

}
