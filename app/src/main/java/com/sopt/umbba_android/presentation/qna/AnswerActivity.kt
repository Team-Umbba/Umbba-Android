package com.sopt.umbba_android.presentation.qna

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityAnswerBinding
import com.sopt.umbba_android.presentation.qna.viewmodel.AnswerViewModel
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity

class AnswerActivity : BindingActivity<ActivityAnswerBinding>(R.layout.activity_answer),
    View.OnClickListener {
    private val viewModel: AnswerViewModel by viewModels { ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.vm = viewModel
        setIntentResponse()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> showBackDialog()
            R.id.iv_check -> {
                setCheckStatus()
            }
        }
    }

    private fun setCheckStatus() {
        if (!viewModel.answer.value.isNullOrBlank()) {
            showConfirmDialog()
        }
    }

    private fun showBackDialog() {
        BackAnswerDialogFragment()
            .show(supportFragmentManager, "BackAnswerDialog")
    }

    private fun setIntentResponse() {
        viewModel.setDataFromIntent(this.intent)
    }

    private fun showConfirmDialog() {
        val bundle = Bundle()
        ConfirmAnswerDialogFragment().apply {
            arguments = viewModel.setBundleArgument(bundle)
            show(supportFragmentManager, "ConfirmDialogFragment")
        }
    }
}