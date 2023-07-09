package com.sopt.umbba_android.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityMainBinding
import com.sopt.umbba_android.presentation.home.HomeFragment
import com.sopt.umbba_android.presentation.qna.QuestionAnswerActivity
import com.sopt.umbba_android.util.binding.BindingActivity

class MainActivity :BindingActivity<ActivityMainBinding>(R.layout.activity_main){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}