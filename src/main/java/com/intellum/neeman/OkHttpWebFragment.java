package com.intellum.neeman;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by arsent on 2016-05-23.
 */
public abstract class OkHttpWebFragment extends WebFragment implements Callback {

    public static final String TAG = "WebFragment";

    private static OkHttpClient mHttpClient;
    private Call mCall;

    private static OkHttpClient getHttpClient() {
        if (mHttpClient == null) {
            mHttpClient = new OkHttpClient();
        }

        return mHttpClient;
    }

    public Call downloadAsync(String url, Callback callback) {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = getHttpClient().newCall(request);
        call.enqueue(callback);
        return call;
    }

    @Override
    public void onResponse(final Call call, final Response response) throws IOException {
        final String responseString = response.body().string();
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vWebView.loadData(responseString, "text/html; charset=utf-8", "UTF-8");
            }
        });
    }

    @Override
    public void onFailure(Call call, IOException e) {
        Log.d(TAG, "FAILED");
        e.printStackTrace();
    }

    public void loadContent() {
        String url = getUrl();
        mCall = downloadAsync(url, this);
    }
}
