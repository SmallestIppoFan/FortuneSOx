package com.example.newversion.Screender

import android.os.Bundle
import android.webkit.WebView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ViewModel

class WebVewModel:ViewModel() {
    var webView: WebView? = null
    var webViewState: Bundle? = null

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() {
        webView?.saveState(Bundle())
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate(savedInstanceState: Bundle?) {
        webView?.restoreState(savedInstanceState!!)
    }
}