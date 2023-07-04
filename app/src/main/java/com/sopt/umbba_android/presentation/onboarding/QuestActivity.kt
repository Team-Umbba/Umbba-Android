package com.sopt.umbba_android.presentation.onboarding

import android.os.Bundle
import com.sopt.umbba_android.R
import androidx.fragment.app.Fragment
import com.sopt.umbba_android.databinding.ActivityQuestBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class QuestActivity : BindingActivity<ActivityQuestBinding>(R.layout.activity_quest) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFragment(QuestSubFragment())
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_question, fragment)
            .commit()
    }
}