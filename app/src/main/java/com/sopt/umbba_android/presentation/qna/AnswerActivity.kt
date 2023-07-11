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
    private val answerViewModel by viewModels <AnswerViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.vm = answerViewModel
        observeText()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> showBackDialog()
            R.id.iv_check -> showConfirmDialog()
        }
    }

    private fun observeText(){
        answerViewModel.inputAnswer.observe(this){
           Log.e("answer 값",it.toString())
        }
    }

    private fun showBackDialog() {
        BackAnswerDialogFragment()
            .show(supportFragmentManager, "BackAnswerDialog")
    }

    private fun showConfirmDialog() {
        val bundle = Bundle()
        val confirmDialog = ConfirmAnswerDialogFragment()
        bundle.putString("ConfirmAnswerText",answerViewModel.inputAnswer.value.toString())
        // TODO(API에따라 다르겠지만, 질문 대주제와 소주제, 질문도 dialog에 넘겨줘야함.)
        confirmDialog.apply{
            arguments = bundle
            show(supportFragmentManager,"ConfirmDialogFragment")
        }
    }
}