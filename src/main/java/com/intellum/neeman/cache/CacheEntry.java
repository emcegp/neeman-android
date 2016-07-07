package com.intellum.neeman.cache;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by arsent on 2016-07-06.
 */
public class CacheEntry {

    @StringDef({MIME_PLAINTEXT, MIME_HTML, MIME_JSON, MIME_JS, MIME_CSS, MIME_PNG, MIME_DEFAULT_BINARY, MIME_XML})
    @Retention(RetentionPolicy.SOURCE)
    public @interface MimeType {}

    public static final String MIME_PLAINTEXT = "text/plain";
    public static final String MIME_HTML = "text/html";
    public static final String MIME_JS = "text/javascript";
    public static final String MIME_CSS = "text/css";
    public static final String MIME_PNG = "image/png";
    public static final String MIME_DEFAULT_BINARY = "application/octet-stream";
    public static final String MIME_XML = "text/xml";
    public static final String MIME_JSON = "application/json";


    @StringDef({ENCODING_UTF8})
    @Retention(RetentionPolicy.SOURCE)
    public @interface Encoding {}

    public static final String ENCODING_UTF8 = "UTF-8";

    private String uri;
    private long time;
    private String encoding;
    private String mimeType;
    private String fileName;

    public CacheEntry(String uri, long time, @MimeType String mimeType, @Encoding String encoding, String fileName) {
        this.uri = uri;
        this.time = time;
        this.encoding = encoding;
        this.mimeType = mimeType;
        this.fileName = fileName;
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

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
