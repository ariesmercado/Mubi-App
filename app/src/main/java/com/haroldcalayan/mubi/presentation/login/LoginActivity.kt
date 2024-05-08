package com.haroldcalayan.mubi.presentation.login

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import com.haroldcalayan.mubi.R
import com.haroldcalayan.mubi.R.drawable
import com.haroldcalayan.mubi.R.string
import com.haroldcalayan.mubi.common.ui.theme.MubiTheme
import com.haroldcalayan.mubi.presentation.authentication.AuthenticationActivity
import com.haroldcalayan.mubi.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class LoginActivity : ComponentActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            this.setKeepOnScreenCondition {
                viewModel.isLoading.value
            }
        }

        setContent {
            if (hasValidSession()) {
                openMain()
                finishAffinity()
            } else LoginComposable()
        }

        subscribeState()
    }

    override fun onResume() {
        super.onResume()

        if (hasValidSession()) {
            openMain()
            finishAffinity()
        }
    }

    private fun subscribeState() {
        lifecycleScope.launchWhenCreated {
            viewModel.requestState.collectLatest {
                it.data?.requestToken?.let { token ->
                    openAuth(token)
                }
            }
        }
    }

    @Composable
    fun LoginComposable() {
        MubiTheme {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Image(
                        painter = painterResource(id = drawable.mubi_login_logo),
                        contentDescription = "mubi logo"
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Text(
                        text = getString(string.login_text_sign_in),
                        style = MaterialTheme.typography.subtitle1,
                        color = colorResource(R.color.font_login)
                    )
                    Spacer(modifier = Modifier.height(32.dp))
                    Button(modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 16.dp),
                        onClick = {
                            onLoginClick()
                        }) {
                        Text(text = getString(string.login_button_login))
                    }
                }
            }
        }
    }

    @Preview
    @Composable
    fun ComposablePreview() {
        LoginComposable()
    }

    private fun onLoginClick() {
        viewModel.getRequestToken()
    }

    private fun openMain() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
    }

    private fun hasValidSession() = viewModel.sessionID.value.isNotEmpty()

    private fun openAuth(requestToken: String) {
        startActivity(
            AuthenticationActivity.newIntent(
                this,
                requestToken,
            )
        )
    }
}