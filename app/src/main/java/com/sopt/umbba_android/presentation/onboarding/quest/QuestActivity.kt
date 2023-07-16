package com.sopt.umbba_android.presentation.onboarding.quest

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.sopt.umbba_android.databinding.ActivityQuestBinding
import com.sopt.umbba_android.presentation.onboarding.NotifyTimeActivity
import com.sopt.umbba_android.presentation.onboarding.SetTimeActivity
import com.sopt.umbba_android.util.binding.BindingActivity

class QuestActivity : BindingActivity<ActivityQuestBinding>(R.layout.activity_quest),
    View.OnClickListener {

    private val viewModel by viewModels<QuestViewModel>()
    private var count = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewmodel = viewModel

        initFragment(QuestOneFragment())
        observeButtonEnabled()
        clickNextButton()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> {
                if (count in 2..5) {
                    supportFragmentManager.popBackStack("$count", FragmentManager.POP_BACK_STACK_INCLUSIVE)
                    binding.progressBar.progress -= 20
                    count -= 1
                } else {
                    finish()
                }
            }
        }
    }

    private fun initFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_question, fragment)
            .addToBackStack("$count")
            .commit()
    }

    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager.commit {
            replace(R.id.fragment_question, fragment)
                .addToBackStack("$count")
        }
    }

    private fun observeButtonEnabled() {
        viewModel.isClickedComplete.observe(this) {
            binding.btnNext.isEnabled = true
        }
    }

    private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            count += 1
            when (count) {
                1 -> changeFragment(QuestOneFragment())
                2 -> changeFragment(QuestTwoFragment())
                3 -> changeFragment(QuestThreeFragment())
                4 -> changeFragment(QuestFourFragment())
                5 -> changeFragment(QuestFiveFragment())
                6 -> {
                    if (true) { //초대하는 측
                        startActivity(Intent(this, SetTimeActivity::class.java))
                    } else { //초대받는 측
                        startActivity(Intent(this, NotifyTimeActivity::class.java))
                    }
                }
            }
            binding.progressBar.progress += 20
        }
    }
}