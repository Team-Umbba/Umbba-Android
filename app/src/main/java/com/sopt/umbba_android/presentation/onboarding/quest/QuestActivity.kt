package com.sopt.umbba_android.presentation.onboarding.quest

import android.os.Bundle
import android.view.View
import com.sopt.umbba_android.R
import androidx.fragment.app.Fragment
import com.sopt.umbba_android.databinding.ActivityQuestBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class QuestActivity : BindingActivity<ActivityQuestBinding>(R.layout.activity_quest),
    View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        changeFragment(QuestOneFragment())
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_question, fragment)
            .commit()
    }
}