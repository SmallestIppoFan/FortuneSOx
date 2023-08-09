package com.example.newversion.Screender

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.ViewGroup
import android.webkit.*
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.newversion.Navigacia.ScreensOfNav

@SuppressLint("SetJavaScriptEnabled", "CoroutineCreationDuringComposition")
@Composable
fun ScreenThatMain(navController: NavController, viewModel: WebVewModel = viewModel()) {
    val context = LocalContext.current as Activity
    var currentUrl by remember { mutableStateOf("") }
    val mUploadMessage = remember { mutableStateOf<ValueCallback<Array<Uri>>?>(null) }
    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            // обработка результата
            if (null == mUploadMessage.value) return@rememberLauncherForActivityResult
            val resultUri = if (data == null || result.resultCode != Activity.RESULT_OK) null else data.data
            mUploadMessage.value?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(result.resultCode, data))
            mUploadMessage.value = null
        }
    }
    val webView = remember {
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
            )
            val cookieManager = CookieManager.getInstance()
            cookieManager.setAcceptCookie(true)
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                cacheMode = WebSettings.LOAD_DEFAULT
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
            }
            webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?,
                                                      request: WebResourceRequest?): Boolean {
                    currentUrl = request?.url.toString()
                    return false
                }
                override fun onReceivedError(view: WebView,
                                             request: WebResourceRequest,
                                             error: WebResourceError) {

                }
                override fun onPageFinished(view: WebView?,
                                            url: String?) {
                    evaluateJavascript("(function() { return document.body.innerHTML; })()") { result ->
                        val resultWithoutQuotes =
                            result?.substring(1, result.length - 1)
                        Log.d("SASA","$resultWithoutQuotes")
                        if (resultWithoutQuotes.equals("")) {
                            this@apply.stopLoading() // Остановить загрузку вместо уничтожения
                            navController.navigate(ScreensOfNav.ScreenThatSplash.name){
                                popUpTo(0)
                            }
                        }
                    }
                }
            }
            webChromeClient = object : WebChromeClient() {

                override fun onShowFileChooser(
                    webView: WebView?, filePathCallback: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?
                ): Boolean {
                    mUploadMessage.value?.onReceiveValue(null)
                    mUploadMessage.value = filePathCallback
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "*/*" // Можно изменить на конкретный тип файлов, который вы хотите загрузить

                    try {
                        launcher.launch(Intent.createChooser(intent, "File Chooser"))
                    } catch (e: ActivityNotFoundException) {
                        mUploadMessage.value = null
                        Toast.makeText(context, "Cannot open file chooser", Toast.LENGTH_LONG).show()
                        return false
                    }
                    return true
                }
            }

            setOnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK
                    && event.action == MotionEvent.ACTION_UP
                    && canGoBack()
                ) { goBack()
                    true
                } else false
            }
        }
    }
    LaunchedEffect("") {
        if (viewModel.webViewState != null) { webView.restoreState(viewModel.webViewState!!)
        } else {
            webView.loadUrl("https://fortune-oxi.cfd/KDMbX844")
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) { AndroidView({ webView }) }
    DisposableEffect(Unit) { onDispose {
            viewModel.webViewState = Bundle().apply {
                webView.saveState(this)
            }
        }
    }
}
