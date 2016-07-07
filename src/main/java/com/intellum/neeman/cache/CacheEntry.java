package com.intellum.neeman.cache;

/**
 * Created by arsent on 2016-07-06.
 */
public class CacheEntry {
    private String uri;
    private long time;
    private String encoding;
    private String mimeType;

    public CacheEntry(String uri, long time, String encoding, String mimeType) {
        this.uri = uri;
        this.time = time;
        this.encoding = encoding;
        this.mimeType = mimeType;
    }

    public String getUrl() {
        return uri;
    }

    public void setUrl(String uri) {
        this.uri = uri;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getName(){
        return "" + uri.hashCode() + mimeType.hashCode();
    }
}
