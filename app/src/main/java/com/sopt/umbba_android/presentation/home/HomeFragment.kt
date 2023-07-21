package com.sopt.umbba_android.presentation.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.model.response.HomeCaseResponseDto
import com.sopt.umbba_android.databinding.FragmentHomeBinding
import com.sopt.umbba_android.presentation.home.viewmodel.HomeViewModel
import com.sopt.umbba_android.presentation.qna.NoOpponentDialogFragment
import com.sopt.umbba_android.presentation.qna.QuestionAnswerActivity
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingFragment
import com.sopt.umbba_android.util.setOnSingleClickListener

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireActivity()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        observeData()
    }
    private fun setClickEvent(responseCaseDto: HomeCaseResponseDto.HomeCaseData) {
        binding.btnAnswer.setOnSingleClickListener {
            viewModel.getResponseCase()
            when (responseCaseDto.responseCase) {
                1 -> startActivity(Intent(requireActivity(), QuestionAnswerActivity::class.java))
                2 -> showInviteDialog(responseCaseDto.inviteUserName.toString(), responseCaseDto.inviteCode.toString())
                3 -> showNoOpponentDialog()
            }
        }
    }

    private fun observeData() {
        viewModel.homeData.observe(requireActivity()) {
            setBackground(it.section)
        }
        viewModel.responseCaseData.observe(requireActivity()) {
            setClickEvent(it)
        }
    }

    private fun showInviteDialog(inviteUserName: String, inviteCode: String) {
        InviteCodeDialogFragment(inviteUserName, inviteCode).show(
            requireActivity().supportFragmentManager,
            "InviteCodeDialogFragment"
        )
    }

    private fun showNoOpponentDialog() {
        NoOpponentDialogFragment().show(
            requireActivity().supportFragmentManager,
            "NoOpponentDialogFragment"
        )
    }

    private fun setBackground(section: String) {
        binding.ivBackground.load(
            when (section) {
                getString(R.string.section1) -> R.drawable.bg_home1
                getString(R.string.section2) -> R.drawable.bg_home2
                getString(R.string.section3) -> R.drawable.bg_home3
                getString(R.string.section4) -> R.drawable.bg_home4
                else -> R.drawable.bg_home5
            }
        )
    }

    override fun onResume() {
        super.onResume()
        viewModel.getResponseCase()
    }
}