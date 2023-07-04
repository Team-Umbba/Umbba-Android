package com.sopt.umbba_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sopt.umbba_android.presentation.onboarding.GoPastActivity
import com.sopt.umbba_android.presentation.onboarding.InviteCodeActivity
import com.sopt.umbba_android.presentation.onboarding.QuestActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, GoPastActivity::class.java))
    }
}