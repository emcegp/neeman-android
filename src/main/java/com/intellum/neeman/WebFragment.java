package com.intellum.neeman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public abstract class WebFragment extends Fragment implements IWebFragment{

    public static final String TAG = "WebFragment";
    public static final String URL_KEY = "URL";


    WebView vWebView;

    protected String mUrl;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: throw exception if there is no url in arguments
        setUrl(getArguments().getString(URL_KEY));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.default_web_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        vWebView = (WebView) view.findViewById(R.id.webview);
        setupWebView();
        loadContent();
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    protected void setupWebView(){
        vWebView.setWebViewClient(getWebClient());
        vWebView.getSettings().setJavaScriptEnabled(true);
        vWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
    }

    public void addFragment(Fragment fragment){
        getActivity().
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.web_fragment_container, fragment, "")
                .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                .addToBackStack("")
                .commit();
    }
}
