package com.mitbbs.summary.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by jc on 2017/6/1.
 */
public class HttpUtil {

    public static InputStream download(String key) throws IOException {
        HttpURLConnection connection = (HttpURLConnection)new URL(key).openConnection();
        return connection.getInputStream();
    }
}
