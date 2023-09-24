package com.ubcompany.umbba_android.presentation.splash

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivitySplashBinding
import com.ubcompany.umbba_android.presentation.login.LoginActivity
import com.ubcompany.umbba_android.util.binding.BindingActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
@AndroidEntryPoint
class SplashActivity : BindingActivity<ActivitySplashBinding>(R.layout.activity_splash) {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        initLogoGif()
        loadSplashScreen()
    }

    private fun initLogoGif() {
        val imageLoader = ImageLoader.Builder(applicationContext)
            .components {
                if (SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()

        binding.ivLogoGif.load(R.raw.splash_logo, imageLoader = imageLoader)
    }

    private fun loadSplashScreen() {
        lifecycleScope.launch {
            delay(1400L)
            goLoginActivity()
        }
    }

    private fun goLoginActivity() {
        startActivity(
            Intent(
                this,
                LoginActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
        finish()
    }
}