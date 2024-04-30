package com.ubcompany.umbba_android.presentation.mypage

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.app.NotificationCompat.getColor
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.FragmentCloserCheckResultBinding
import com.ubcompany.umbba_android.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GetCloserResultFragment(private val viewModel: GetCloserViewModel) :
    BindingFragment<FragmentCloserCheckResultBinding>(R.layout.fragment_closer_check_result) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel
        setResultFragmentData()
        initChangeQuestionFragment()
        changeQuestionFragment()
    }

    private fun changeQuestionFragment() {
        binding.btnNext.setOnClickListener {
            viewModel.patchNextQuestion()
            viewModel.showQuestionFragment()
        }
    }

    private fun setResultFragmentData() {
        viewModel.closerQuestionResponse.observe(viewLifecycleOwner) {
            with(binding) {
                tvMyChoiceResult.text = it.myChoice
                if (it.responseCase == OPPONENT_NO_ANSWER) {
                    tvResult.text = "아직 상대가\n선택하지 않았어요"
                    tvOtherChoiceResult.text = "-"
                    btnNext.visibility = View.INVISIBLE
                    setConnectView(false)
                } else {
                    tvOtherChoiceResult.text = it.opponentChoice
                    if (it.responseCase == BOTH_ANSWER_SAME) {
                        setConnectView(true)
                        tvResult.text = "우리는 통했어요"
                    }

                    if (it.responseCase == BOTH_ANSWER_DIFFERENT) {
                        setConnectView(false)
                        tvResult.text = "선택이 엇갈렸어요"
                    }
                    btnNext.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun initChangeQuestionFragment() {
        viewModel.changeQuestionFragment.value = false
    }

    private fun setConnectView(isSame: Boolean) {
        with(binding) {
            if (isSame) {
                ivResult.setImageResource(R.drawable.ic_connect)
                tvOtherChoiceResult.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.primary_500
                    )
                )
            } else {
                ivResult.setImageResource(R.drawable.ic_closer_result_diff)
                tvOtherChoiceResult.setTextColor(
                    ContextCompat.getColor(
                        requireContext(),
                        R.color.grey_800
                    )
                )
            }
        }

    }

    companion object {
        const val OPPONENT_NO_ANSWER = 2
        const val BOTH_ANSWER_SAME = 3
        const val BOTH_ANSWER_DIFFERENT = 4
    }

}
