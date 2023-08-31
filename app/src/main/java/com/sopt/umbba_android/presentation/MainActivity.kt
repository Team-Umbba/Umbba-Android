package com.sopt.umbba_android.presentation

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.load
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityMainBinding
import com.sopt.umbba_android.presentation.home.HomeFragment
import com.sopt.umbba_android.presentation.list.ListFragment
import com.sopt.umbba_android.presentation.setting.SettingFragment
import com.sopt.umbba_android.util.binding.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    private lateinit var context: Context
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        context = this
        initView()
        initLoadingGif()
        setBottomNav()
    }

    private fun initView() {
        setLoadingView(true)
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
        if (currentFragment == null) {
            changeFragment(HomeFragment())
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

        binding.ivLogoGif.load(R.raw.splash_logo, imageLoader = imageLoader)
    }

    private fun setBottomNav() {
        binding.bnvMain.run {
            setOnItemSelectedListener {
                changeFragment(
                    when (it.itemId) {
                        R.id.menu_home -> {
                            setLoadingView(true)
                            HomeFragment()
                        }
                        R.id.menu_setting -> {
                            setLoadingView(false)
                            SettingFragment()
                        }
                        else -> {
                            setLoadingView(false)
                            ListFragment()
                        }
                    }
                )
                true
            }
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fcv_main, fragment)
            .commit()
    }

    private fun setLoadingView(boolean: Boolean) {
        if (boolean) {
            binding.clLoading.visibility = View.VISIBLE
        } else {
            binding.clLoading.visibility = View.GONE
        }
    }

    fun getLoadingView(): View = binding.clLoading
}