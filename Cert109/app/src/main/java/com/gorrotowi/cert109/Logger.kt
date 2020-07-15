package com.gorrotowi.cert109

import android.util.Log

fun Any.loge(msg: Any?, error: Throwable? = null) {
    if (BuildConfig.ISDEVELOP) {
        Log.e(this::class.java.simpleName, "--> $msg", error)
    }
}