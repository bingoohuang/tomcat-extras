package com.github.bingoohuang.tomcat.extras.utils;

import org.apache.catalina.Context;

public class Utils {
    public static String getContextPath(Context context) {
        String path = context.getServletContext().getContextPath();

        return (isEmpty(path)) ? "/" : path;
    }

    public static String getContextName(Context context) {
        String name = context.getName();

        if (isEmpty(name)) return "ROOT";
        return name.replaceAll("/", "");
    }

    public static boolean isEmpty(String name) {
        return name == null || name.length() == 0;
    }
}
