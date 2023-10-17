package com.ubcompany.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityCommunicationBinding
import com.ubcompany.umbba_android.domain.entity.User
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CommunicationActivity :
    BindingActivity<ActivityCommunicationBinding>(R.layout.activity_communication) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        goInputInfoActivity()
        initLoadingGif()
        animateFadeBackground()
    }

    private fun goInputInfoActivity() {
        binding.btnStart.setOnSingleClickListener {
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("userData", User::class.java)
            } else {
                intent.getParcelableExtra<User>("userData")
            }
            startActivity(Intent(this, InputInfoActivity::class.java).apply {
                putExtra("userData", userData)
            })
        }
    }

    private fun initLoadingGif() {
        val imageLoader = ImageLoader.Builder(applicationContext)
            .components {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder.Factory())
                } else {
                    add(GifDecoder.Factory())
                }
            }.build()

        binding.ivGif.load(R.raw.communication, imageLoader = imageLoader)
    }

    private fun animateFadeBackground() {
        Handler(Looper.getMainLooper()).postDelayed({
            val fadeOutAnim = AnimationUtils.loadAnimation(this, R.anim.fadeout_short)
            with(binding.clStart) {
                startAnimation(fadeOutAnim)
                visibility = View.INVISIBLE
            }
        }, 2000)
        Handler(Looper.getMainLooper()).postDelayed({
            with(binding.clEnd) {
                visibility = View.VISIBLE
            }
        }, 3000)
    }

}