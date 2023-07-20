package com.sopt.umbba_android.presentation.onboarding.quest

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.sopt.umbba_android.databinding.ActivityQuestBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.presentation.onboarding.NotifyTimeActivity
import com.sopt.umbba_android.presentation.onboarding.SetTimeActivity
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity

class QuestActivity : BindingActivity<ActivityQuestBinding>(R.layout.activity_quest),
    View.OnClickListener {

    private val viewModel by viewModels<QuestViewModel>() { ViewModelFactory(this) }
    private var count = 0
    private var quest = arrayOfNulls<String>(5)
    private val questList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.viewmodel = viewModel
        binding.clickListener = this

        initFragment(QuestOneFragment())
        checkNextButtonEnabled()
        clickNextButton()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> {
                if (count in 1..4) {
                    supportFragmentManager.popBackStack(
                        "$count",
                        FragmentManager.POP_BACK_STACK_INCLUSIVE
                    )
                    binding.progressBar.progress -= 20
                    count -= 1
                    initChip()
                    setBeforeButtonClick(count)
                } else {
                    finish()
                }
            }
        }
    }

    private fun setBeforeButtonClick(count: Int) {
        when (quest[count].toString()) {
            "응" -> viewModel.isClickedYes.value = true
            "아니" -> viewModel.isClickedNo.value = true
            "애매해" -> viewModel.isClickedAmbiguous.value = true
        }
        Log.d("viewmodel", "pop stack : ${count}번 : ${quest[count].toString()}")
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

    private fun checkNextButtonEnabled() {
        viewModel.isClickedComplete.observe(this) {
            binding.btnNext.isEnabled = true
        }
    }

    private fun clickNextButton() {
        binding.btnNext.setOnClickListener {
            Log.d("viewmodel", "chipText : ${viewModel.clickedChipText.value.toString()}")
            Log.d("viewmodel", "count : ${count}")
            quest[count] = viewModel.clickedChipText.value.toString()
            Log.d("viewmodel", "배열 값 : ${quest[count]}")
            initChip()
            count += 1
            when (count) {
                0 -> changeFragment(QuestOneFragment())
                1 -> changeFragment(QuestTwoFragment())
                2 -> changeFragment(QuestThreeFragment())
                3 -> changeFragment(QuestFourFragment())
                4 -> changeFragment(QuestFiveFragment())
                5 -> {
                    goNextActivity()
                    count = 4
                }
            }
            binding.progressBar.progress += 20
        }
    }

    private fun initChip() {
        with(viewModel) {
            isClickedYes.value = false
            isClickedNo.value = false
            isClickedAmbiguous.value = false
        }
    }

    private fun setQuestList(questData: Array<String?>) {
        for (i: Int in 0..4) {
            questList.add(questData[i].toString())
        }
    }

    private fun goNextActivity() {
        Log.e("yeonjin", "${quest[0]}${quest[1]}${quest[2]}${quest[3]}${quest[4]}")
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("userData", User::class.java)
        } else {
            intent.getParcelableExtra<User>("userData")
        }
        if (userData != null && !userData.isReceiver) {
            Log.e("yeonjin", "quest parcelable : ${userData?.isReceiver}")
            startActivity(Intent(this, SetTimeActivity::class.java).apply {
                putExtra("questData", quest)
                putExtra("userData", userData)
            })
        } else {
            Log.e("yeonjin", "quest parcelable : ${userData?.isReceiver}")
            setQuestList(quest)
            viewModel.setReceiveInfo(userData, questList)
            viewModel.isPostSuccess.observe(this) {
                if (it) {
                    startActivity(Intent(this, NotifyTimeActivity::class.java))
                }
            }
        }
    }
}