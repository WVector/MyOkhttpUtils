package com.zhy.http.okhttp.intercepter;

import java.io.IOException;

/**
 * Created by admin on 2017/3/10.
 */

public class NoNetWorkException extends IOException {
    public NoNetWorkException() {
    }

    public NoNetWorkException(String message) {
        super(message);
    }
}
