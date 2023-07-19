package com.sopt.umbba_android.presentation.qna

import android.os.Bundle
import android.util.Log
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
        setQuestionTitle()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_qna_back -> showBackDialog()
            R.id.iv_check -> {
                if (!viewModel.answer.value.isNullOrBlank()) {
                    showConfirmDialog()
                }
            }
        }
    }

    private fun showBackDialog() {
        BackAnswerDialogFragment()
            .show(supportFragmentManager, "BackAnswerDialog")
    }

    private fun setQuestionTitle() {
        with(binding) {
            tvQuestion.text = intent.getStringExtra("question")
            tvTopic.text = intent.getStringExtra("topic")
            viewModel.appbarTitle.value = intent.getStringExtra("section")
            layoutAppbar.titleText = viewModel.appbarTitle.value
        }
    }

    private fun showConfirmDialog() {
        val bundle = Bundle()
        val confirmDialog = ConfirmAnswerDialogFragment()
        bundle.apply {
            putString("question", intent.getStringExtra("question"))
            putString("topic", intent.getStringExtra("topic"))
            putString("section", intent.getStringExtra("section"))
            putString("answer", viewModel.answer.value)
        } // 이 부분은 변수 안쓰고.. 코드 쓰고 싶음 (일단 서버연결 완료되면 해보고, 테스트도 해보자잉)
        confirmDialog.apply {
            arguments = bundle
            show(supportFragmentManager, "ConfirmDialogFragment")
        }
    }
}