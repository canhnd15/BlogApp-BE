package com.davidnguyen.blogs.singleton;

import java.text.SimpleDateFormat;

public class DateFormatter {
    private static final SimpleDateFormat INSTANCE = new SimpleDateFormat("yyyy-MM-dd");

    private DateFormatter(){};

    public static SimpleDateFormat getInstance() {
        return INSTANCE;
    }
}
