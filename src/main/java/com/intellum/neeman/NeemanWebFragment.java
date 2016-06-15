package com.intellum.neeman;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.widget.ProgressBar;

import java.io.InputStream;

public abstract class NeemanWebFragment extends Fragment implements IWebFragment, NeemanPageListener{

    public static final String DEFAULT_CSS_FILE = "main.css";


    public static final String TAG = "WebFragment";
    public static final String URL_KEY = "URL";
    public NeemanPageListener listener;

    WebView vWebView;
    ProgressBar vProgress;

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
        vProgress = (ProgressBar) view.findViewById(R.id.progress_bar);

        setupWebView();
        loadContent();
    }

    public String getUrl() {
        return mUrl;
    }
    public String getCookies() {
        return null;
    }

    public void setUrl(String url) {
        this.mUrl = url;
    }

    protected void setupWebView(){
        setupWebviewClient();
        setupWebviewSettings();
        setupWebviewCookies();
    }

    private void setupWebviewClient(){
        NeemanWebViewClient client = getWebClient();
        if (client != null) {
            client.setListener(this);
            vWebView.setWebViewClient(client);
        }
    }

    private void setupWebviewSettings(){
        vWebView.getSettings().setJavaScriptEnabled(true);
        vWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

    }

    private void setupWebviewCookies(){
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        String cookies = getCookies();
        if (cookies != null && !cookies.isEmpty()){
            cookieManager.setCookie("intellumgoals.com", cookies);
        }
    }

    @Override
    public void onPageFinished() {
        setWebViewVisible(true);
        setProgressVisible(false);
        populateTitle();
    }

    @Override
    public void onPageStarted() {
        setWebViewVisible(false);
        setProgressVisible(true);
    }

    protected void setWebViewVisible(boolean isVisible){
        if (isVisible){
            vWebView.setVisibility(View.VISIBLE);
        }else{
            vWebView.setVisibility(View.GONE);
        }
    }

    protected void setProgressVisible(boolean isVisible){
        if (isVisible){
            vProgress.setVisibility(View.VISIBLE);
        }else{
            vProgress.setVisibility(View.GONE);
        }
    }

    protected void populateTitle(){
        getActivity().setTitle(vWebView.getTitle());
    }

    public String getCSSContent(){
        return getCSSContent(DEFAULT_CSS_FILE);
    }

    public String getCSSContent(String cssFileName) {
        try {
            InputStream inputStream = getResources().getAssets().open(cssFileName);
            byte[] buffer = new byte[inputStream.available()];
            inputStream.read(buffer);
            inputStream.close();
            return new String(buffer);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }
}
