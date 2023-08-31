package com.sopt.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivitySelectFamilyBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.presentation.onboarding.quest.QuestActivity
import com.sopt.umbba_android.presentation.onboarding.viewmodel.SelectFamilyViewModel
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setOnSingleClickListener

class SelectFamilyActivity :
    BindingActivity<ActivitySelectFamilyBinding>(R.layout.activity_select_family),
    View.OnClickListener {

    private val viewModel by viewModels<SelectFamilyViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.viewmodel = viewModel

        checkAllSelectFamilyComplete()
        goQuestActivity()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun checkAllSelectFamilyComplete() {
        viewModel.parent.observe(this) {
            viewModel.checkSelectFamily()
            addMoreQuestion()
            modifyMoreQuestion()
        }
        viewModel.child.observe(this) {
            viewModel.checkSelectFamily()
            addMoreQuestion()
            modifyMoreQuestion()
        }
        viewModel.mom.observe(this) {
            viewModel.checkSelectFamily()
        }
        viewModel.dad.observe(this) {
            viewModel.checkSelectFamily()
        }
        viewModel.isAllSelectFamily.observe(this) {
            binding.btnNext.isEnabled = it
        }
    }

    private fun addMoreQuestion() {
        if (binding.chip1.isCheckable || binding.chip2.isCheckable) {
            binding.clMore.visibility = View.VISIBLE
        }
    }

    private fun modifyMoreQuestion() {
        if (binding.chip1.isChecked) {
            binding.chip3.text = getString(R.string.mom)
            binding.chip4.text = getString(R.string.dad)
        } else {
            binding.chip3.text = getString(R.string.son)
            binding.chip4.text = getString(R.string.daughter)
        }
    }

    private fun saveInfo(user: User) {
        with(binding) {
            user.isInvitorChild = chip1.isChecked
            user.relationInfo =
                if (chip3.isChecked) {
                    chip3.text.toString()
                } else {
                    chip4.text.toString()
                }
        }

    }

    private fun goQuestActivity() {
        binding.btnNext.setOnSingleClickListener {
            val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("userData", User::class.java)
            } else {
                intent.getParcelableExtra<User>("userData")
            }
            if (userData != null) {
                saveInfo(userData)
            }
            startActivity(Intent(this, QuestActivity::class.java).apply {
                putExtra("userData", userData)
            })
        }
    }
}