package com.intellum.neeman.impl;

import android.content.Intent;
import android.os.Bundle;

import com.intellum.neeman.NativeWebFragment;
import com.intellum.neeman.NeemanWebViewClient;

/**
 * Created by arsent on 2016-05-31.
 */
public class DefaultWebFragment extends NativeWebFragment {

    public static DefaultWebFragment newInstance(String url) {
        Bundle args = new Bundle();
        args.putString(URL_KEY, url);
        DefaultWebFragment fragment = new DefaultWebFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void addWebFragment(String url) {
        Intent intent = new Intent(getActivity(), NeemanWebActivity.class);
        intent.putExtra("url", url);
        getActivity().startActivity(intent);
    }

    @Override
    public NeemanWebViewClient getWebClient() {
        return new DefaultWebViewClient(this);
    }
}
