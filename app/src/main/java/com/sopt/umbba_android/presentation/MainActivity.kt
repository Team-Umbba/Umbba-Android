package com.sopt.umbba_android.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityMainBinding
import com.sopt.umbba_android.presentation.home.HomeFragment
import com.sopt.umbba_android.presentation.qna.QuestionAnswerActivity
import com.sopt.umbba_android.presentation.setting.SettingFragment
import com.sopt.umbba_android.util.binding.BindingActivity

class MainActivity :BindingActivity<ActivityMainBinding>(R.layout.activity_main){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        setBottomNav()
    }

    private fun initView(){
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fcv_main)
        if (currentFragment == null) {
            changeFragment(HomeFragment())
        }
    }
    private fun setBottomNav(){
        binding.bnvMain.run(){
            setOnItemSelectedListener {
                changeFragment(
                    when(it.itemId){
                        R.id.menu_home -> HomeFragment()
                        R.id.menu_setting -> SettingFragment()
                        else -> HomeFragment()
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