package com.ubcompany.umbba_android.presentation.qna

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityAnswerBinding
import com.ubcompany.umbba_android.presentation.qna.viewmodel.AnswerViewModel
import com.ubcompany.umbba_android.util.ViewModelFactory
import com.ubcompany.umbba_android.util.binding.BindingActivity

class AnswerActivity : BindingActivity<ActivityAnswerBinding>(R.layout.activity_answer),
    View.OnClickListener {
    private val viewModel: AnswerViewModel by viewModels { ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.vm = viewModel
        setIntentResponse()
        setClickBtnSave()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> showBackDialog()
        }
    }

    private fun setClickBtnSave() {
        with(binding) {
            viewModel.answer.observe(this@AnswerActivity) {
                btnSave.isEnabled = !it.isNullOrBlank()
            }
            btnSave.setOnClickListener {
                showConfirmDialog()
            }
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