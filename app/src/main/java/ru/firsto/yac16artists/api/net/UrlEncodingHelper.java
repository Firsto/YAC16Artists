package ru.firsto.yac16artists.api.net;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

public class UrlEncodingHelper {

    public static String encode(final String content, final String encoding) {
        try {
            return URLEncoder.encode(
                    content,
                    encoding != null ? encoding : Charset.defaultCharset().name()
            );
        } catch (UnsupportedEncodingException problem) {
            throw new IllegalArgumentException(problem);
        }
    }

}
