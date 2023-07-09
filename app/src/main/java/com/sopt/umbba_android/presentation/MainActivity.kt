package com.sopt.umbba_android.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sopt.umbba_android.R
import com.sopt.umbba_android.presentation.qna.QuestionAnswerActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startActivity(Intent(this, QuestionAnswerActivity::class.java))
    }
}