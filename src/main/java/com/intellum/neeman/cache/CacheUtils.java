package com.intellum.neeman.cache;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by arsent on 2016-07-06.
 */
public class CacheUtils {

    public static final String TAG = "UrlCache";

    public static final long ONE_SECOND = 1000L;
    public static final long ONE_MINUTE = 60L * ONE_SECOND;
    public static final long ONE_HOUR = 60L * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;

    protected Map<String, CacheEntry> cacheEntries = new HashMap<String, CacheEntry>();
    protected Context context;
    protected File rootDir;

    public static CacheUtils instance;

    public static CacheUtils getInstance() {
        if (instance == null) {
            throw new RuntimeException("Cache Utils has not been initialized yet.");
        }
        return instance;
    }

    public static void newInstance(Context context) {
        if (instance == null) {
            instance = new CacheUtils(context);
        }
    }

    public static void forceNewInstance(Context context) {
        instance = null;
        newInstance(context);
    }

    private CacheUtils(Context context) {
        this.context = context.getApplicationContext();
        this.rootDir = context.getFilesDir();
    }

    public void register(CacheEntry entry) {
        this.cacheEntries.put(entry.getUrl(), entry);
    }

    public WebResourceResponse load(Uri uri) {
        CacheEntry cacheEntry = this.cacheEntries.get(uri.toString());

        if (cacheEntry == null) return null;

        File cachedFile = getCachedFile(cacheEntry);
        if (cachedFile.exists()) {
            if (isCachedFileExpired(cachedFile, cacheEntry)) {
                cachedFile.delete();

                //cached file deleted, call load() again.
                Log.d(TAG, "Deleting from cache: " + uri.toString());
                return load(uri);
            }

            return newWebResourceResponse(cacheEntry, cachedFile);
        } else {
            try {
                downloadAndStore(uri.toString(), cacheEntry, cachedFile);

                //now the file exists in the cache, so we can just call this method again to read it.
                return load(uri);
            } catch (Exception e) {
                Log.d(TAG, "Error reading file over network: " + cachedFile.getPath(), e);
            }
        }

        return null;
    }

    private File getCachedFile(CacheEntry entry) {
        return new File(this.rootDir.getPath() + File.separator + entry.getName());
    }

    private boolean isCachedFileExpired(File file, CacheEntry entry) {
        long cacheEntryAge = System.currentTimeMillis() - file.lastModified();
        return cacheEntryAge > entry.getTime();
    }

    private WebResourceResponse newWebResourceResponse(CacheEntry entry, File file) {
        try {
            return new WebResourceResponse(entry.getMimeType(), entry.getEncoding(), new FileInputStream(file));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "Error loading cached file: " + file.getPath() + " : "
                    + e.getMessage(), e);
        }
        return null;
    }

    private void downloadAndStore(String url, CacheEntry cacheEntry, File cachedFile)
            throws IOException {

        URL urlObj = new URL(url);
        URLConnection urlConnection = urlObj.openConnection();
        InputStream urlInput = urlConnection.getInputStream();

        FileOutputStream fileOutputStream =
                this.context.openFileOutput(cacheEntry.getName(), Context.MODE_PRIVATE);

        int data = urlInput.read();
        while (data != -1) {
            fileOutputStream.write(data);
            data = urlInput.read();
        }

        urlInput.close();
        fileOutputStream.close();
        Log.d(TAG, "Cache file: " + cacheEntry.getName() + " stored. ");
    }
}