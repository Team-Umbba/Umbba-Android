package com.sopt.umbba_android.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityMainBinding
import com.sopt.umbba_android.presentation.home.HomeFragment
import com.sopt.umbba_android.presentation.list.ListFragment
import com.sopt.umbba_android.presentation.setting.SettingFragment
import com.sopt.umbba_android.util.binding.BindingActivity

class MainActivity : BindingActivity<ActivityMainBinding>(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setBottomNav()
        getToken()
    }

    private fun getToken(){
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e("hyeon", "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            Log.e("hyeon", token.toString())
        })
    }

    private fun initView() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
        if (currentFragment == null) {
            changeFragment(HomeFragment())
        }
    }

    private fun setBottomNav() {
        binding.bnvMain.run() {
            setOnItemSelectedListener {
                changeFragment(
                    when (it.itemId) {
                        R.id.menu_home -> HomeFragment()
                        R.id.menu_setting -> SettingFragment()
                        else -> ListFragment()
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
}