package com.ubcompany.umbba_android.presentation.splash

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.util.Log
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.data.local.SharedPreferences
import com.ubcompany.umbba_android.databinding.ActivitySplashBinding
import com.ubcompany.umbba_android.presentation.MainActivity
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
        isUpdateAvailable()
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

    private fun isUpdateAvailable() {
        val appUpdateManager = AppUpdateManagerFactory.create(this)
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE && appUpdateInfo.isUpdateTypeAllowed(
                    AppUpdateType.IMMEDIATE
                )
            ) {
                SharedPreferences.setUpdateAvailableBoolean(IS_UPDATE_AVAILABLE, true)
                Log.e("hyeon","${SharedPreferences.getUpdateAvailableBoolean(IS_UPDATE_AVAILABLE)}")
            } else {
                SharedPreferences.setUpdateAvailableBoolean(IS_UPDATE_AVAILABLE, false)
                Log.e("hyeon","${SharedPreferences.getUpdateAvailableBoolean(IS_UPDATE_AVAILABLE)}")
            }
        }
    }

    companion object {
        const val IS_UPDATE_AVAILABLE = "IS_UPDATE_AVAILABLE"
    }
}