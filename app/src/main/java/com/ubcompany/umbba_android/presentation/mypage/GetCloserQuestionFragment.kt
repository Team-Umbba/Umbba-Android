package com.ubcompany.umbba_android.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.lifecycleScope
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.FragmentCloserQuestionBinding
import com.ubcompany.umbba_android.presentation.MainActivity
import com.ubcompany.umbba_android.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GetCloserQuestionFragment(private val viewModel: GetCloserViewModel) :
    BindingFragment<FragmentCloserQuestionBinding>(R.layout.fragment_closer_question) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        setCloserQuestionView()
        saveQuestionAnswer()
        setResultBtnEnable()
        clickResultBtn()
    }

    private fun setResultBtnEnable() {
        this.viewModel.isCompletedAnswer.observe(viewLifecycleOwner) {
            binding.btnCheckResult.isEnabled = it
        }
    }

    private fun clickResultBtn() {
        binding.btnCheckResult.setOnClickListener {
            viewModel.patchUserAnswer()
            viewModel.showResultFragment()
        }
    }

    private fun saveQuestionAnswer() {
        viewModel.isCheckedAnswerOne.observe(viewLifecycleOwner) {
            viewModel.checkCompletedAnswer()
            viewModel.checkedUserAnswer.value = 1
        }
        viewModel.isCheckedAnswerTwo.observe(viewLifecycleOwner) {
            viewModel.checkCompletedAnswer()
            viewModel.checkedUserAnswer.value = 2
        }
    }

    private fun setCloserQuestionView() {
        viewModel.closerQuestionResponse.observe(viewLifecycleOwner) {
            with(binding) {
                tvQuestion.text = "Q. ${it.balanceQuestion}"
                btnAnswer1.text = it.choiceAnswer1
                btnAnswer2.text = it.choiceAnswer2
            }
        }
    }
}