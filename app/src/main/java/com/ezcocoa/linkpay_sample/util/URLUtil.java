package com.ezcocoa.linkpay_sample.util;

/**
 * Created by hojunbaek on 28/11/2016.
 */

public class URLUtil {
    public static StringBuilder appendStart(StringBuilder sb, String v) {
        return sb.append(v.trim()).append("?");
    }

    public static StringBuilder appendAnd(StringBuilder sb, String k, String v) {
        return sb.append(k).append("=").append(v.trim()).append("&");
    }

    public static StringBuilder append(StringBuilder sb, String k, String v) {
        return sb.append(k).append("=").append(v.trim());
    }
}
