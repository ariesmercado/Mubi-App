package com.haroldcalayan.mubi.presentation.authentication

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.AlertDialog
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.haroldcalayan.mubi.BuildConfig
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    private val viewModel: AuthenticationViewModel by viewModels()
    private lateinit var requestToken: String
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceManager.getDefaultSharedPreferences(this)
        subscribeState()
        setContent {
            requestToken = intent.getStringExtra(KEY_REQUEST_TOKEN).orEmpty()
            AuthenticationContent(requestToken)
        }
    }

    @Composable
    fun SimpleAlertDialog() {
        AlertDialog(
            onDismissRequest = { },
            confirmButton = {
                TextButton(onClick = {
                    onBackPressed()
                })
                { Text(text = "OK") }
            },
            title = { Text(text = getString(R.string.authentication_session_denied_title)) },
            text = { Text(text = getString(R.string.authentication_session_denied_message)) }
        )
    }

    @Composable
    fun AuthenticationContent(requestToken: String) {
        Scaffold(
            content = { WebviewContent(requestToken) }
        )
    }

    @Composable
    fun WebviewContent(requestToken: String) {
        val url = "${BuildConfig.BASE_AUTH_URL}${requestToken}"
        AndroidView(factory = {
            WebView(it).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                webViewClient = ApprovedTokenWebViewClient()

                loadUrl(url)
            }
        }, update = {
            it.loadUrl(url)
        })
    }

    private fun subscribeState() {
        lifecycleScope.launchWhenCreated {
            viewModel.state.collectLatest {
                it.data?.let {
                    openMain()
                    finishAffinity()
                }.run {
                    if (it.error.contains(RESPONSE_ERROR_SESSION_DENIED)) {
                        setContent {
                            SimpleAlertDialog()
                        }
                    }
                }
            }
        }
    }

    private fun openMain() {
        val intent = Intent(this@AuthenticationActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private inner class ApprovedTokenWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            view.loadUrl(url)
            if (url.contains(ENDPOINT_ALLOW)) {
                viewModel.getRequestToken(requestToken)
            } else if (url.contains(ENDPOINT_DENY)) {
                onBackPressed()
            }
            return true
        }
    }

    companion object {
        private const val KEY_REQUEST_TOKEN = "key_request_token"
        private const val ENDPOINT_ALLOW = "/allow"
        private const val ENDPOINT_DENY = "/deny"
        private const val RESPONSE_ERROR_SESSION_DENIED = "Session Denied"

        fun newIntent(context: Context?, token: String) =
            Intent(context, AuthenticationActivity::class.java).apply {
                putExtra(KEY_REQUEST_TOKEN, token)
            }
    }
}