package com.sopt.umbba_android.presentation.qna

import android.os.Bundle
import android.view.View
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityAnswerBinding
import com.sopt.umbba_android.util.binding.BindingActivity

class AnswerActivity : BindingActivity<ActivityAnswerBinding>(R.layout.activity_answer),
    View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
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

    private fun showConfirmDialog() {
        ConfirmAnswerDialogFragment()
            .show(supportFragmentManager, "ConfirmAnswerDialog")
    }
}