package com.sopt.umbba_android.presentation.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.model.response.HomeCaseResponseDto
import com.sopt.umbba_android.databinding.FragmentHomeBinding
import com.sopt.umbba_android.presentation.MainActivity
import com.sopt.umbba_android.presentation.home.viewmodel.HomeViewModel
import com.sopt.umbba_android.presentation.qna.NoOpponentDialogFragment
import com.sopt.umbba_android.presentation.qna.QuestionAnswerActivity
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingFragment
import com.sopt.umbba_android.util.setOnSingleClickListener

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireActivity()) }

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                viewModel.setStateCloseEnding()
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        observeData()
    }


    private fun setClickEvent(responseCaseDto: HomeCaseResponseDto.HomeCaseData) {
        binding.btnAnswer.setOnSingleClickListener {
            when (responseCaseDto.responseCase) {
                1 -> startActivity(Intent(requireActivity(), QuestionAnswerActivity::class.java))
                2 -> showInviteDialog(
                    responseCaseDto.inviteUserName.toString(),
                    responseCaseDto.inviteCode.toString()
                )

                3 -> showNoOpponentDialog()
            }
        }
    }

    private fun observeData() {
        viewModel.homeData.observe(viewLifecycleOwner) {
            setBackground(it.section)
            if (it.index == 8 && viewModel.isShowedEndingPage()) {
                viewModel.setStateObserveIndex()
                startForResult.launch(Intent(requireActivity(), EndingActivity::class.java))
            }
        }
        viewModel.responseCaseData.observe(viewLifecycleOwner) {
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
                getString(R.string.section1) -> R.drawable.img_home1
                getString(R.string.section2) -> R.drawable.img_home2
                getString(R.string.section3) -> R.drawable.img_home3
                getString(R.string.section4) -> R.drawable.img_home4
                else -> R.drawable.img_home5
            }
        )
        Handler(Looper.getMainLooper()).postDelayed({
            if (activity != null) {
                (activity as MainActivity).getLoadingView().visibility = View.INVISIBLE
            }
        }, 1000)
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isObserveIndex.value == false) {
            viewModel.getHomeData()
        }
        viewModel.getResponseCase()
    }
}