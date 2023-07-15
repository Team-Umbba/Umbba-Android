package com.sopt.umbba_android.presentation.qna

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.viewModelScope
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityAnswerBinding
import com.sopt.umbba_android.util.binding.BindingActivity
import timber.log.Timber

class AnswerActivity : BindingActivity<ActivityAnswerBinding>(R.layout.activity_answer),
    View.OnClickListener {
    private val answerViewModel by viewModels<AnswerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.vm = answerViewModel
        setQuestionTitle()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> showBackDialog()
            R.id.iv_check -> showConfirmDialog()
        }
    }

    private fun showBackDialog() {
        BackAnswerDialogFragment()
            .show(supportFragmentManager, "BackAnswerDialog")
    }

    private fun setQuestionTitle() {
        with(binding) {
            tvQuestion.text = intent.getStringExtra("question")
            tvTitle.text = intent.getStringExtra("topic")
            layoutAppbar.titleText = intent.getStringExtra("section").toString()
            Log.e("hyeon",intent.getStringExtra("section").toString())
        }
    }

    private fun showConfirmDialog() {
        val bundle = Bundle()
        val confirmDialog = ConfirmAnswerDialogFragment()
        bundle.apply {
            putString("question", intent.getStringExtra("question"))
            putString("topic", intent.getStringExtra("topic"))
            putString("section", intent.getStringExtra("section"))
            putString("answer", answerViewModel.answer.value)
        }
        confirmDialog.apply {
            arguments = bundle
            show(supportFragmentManager, "ConfirmDialogFragment")
        }
    }
}