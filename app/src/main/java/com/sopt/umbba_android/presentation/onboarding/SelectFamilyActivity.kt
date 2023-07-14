package com.sopt.umbba_android.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivitySelectFamilyBinding
import com.sopt.umbba_android.presentation.viewmodel.SelectFamilyViewModel
import com.sopt.umbba_android.util.binding.BindingActivity

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
            binding.chip3.text = "엄마"
            binding.chip4.text = "아빠"
        } else {
            binding.chip3.text = "아들"
            binding.chip4.text = "딸"
        }
    }

    private fun goQuestActivity() {
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, QuestActivity::class.java))
        }
    }
}