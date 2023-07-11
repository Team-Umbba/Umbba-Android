package com.sopt.umbba_android.presentation.splash

import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityOnboardingBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class OnboardingActivity : BindingActivity<ActivityOnboardingBinding>(R.layout.activity_onboarding) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initLogoGif()
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
}