package com.ubcompany.umbba_android.presentation.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.data.local.SharedPreferences
import com.ubcompany.umbba_android.data.model.response.HomeCaseResponseDto
import com.ubcompany.umbba_android.databinding.FragmentHomeBinding
import com.ubcompany.umbba_android.presentation.MainActivity
import com.ubcompany.umbba_android.presentation.home.viewmodel.HomeViewModel
import com.ubcompany.umbba_android.presentation.qna.NoOpponentDialogFragment
import com.ubcompany.umbba_android.presentation.qna.QuestionAnswerActivity
import com.ubcompany.umbba_android.util.binding.BindingFragment
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels()

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
        isUpdateAppVersion()
    }


    private fun setClickEvent(responseCaseDto: HomeCaseResponseDto.HomeCaseData) {
        binding.btnAnswer.setOnSingleClickListener {
            viewModel.getResponseCase()
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

    private fun showUpdateDialog() {
        UpdateDialogFragment().show(
            requireActivity().supportFragmentManager,
            "UpdateDialogFragment"
        )
    }

    private fun isUpdateAppVersion() {
        Log.e("hyeon","${SharedPreferences.getUpdateAvailableBoolean(IS_UPDATE_AVAILABLE)}")
        if(SharedPreferences.getUpdateAvailableBoolean(IS_UPDATE_AVAILABLE)){
            showUpdateDialog()
        }
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
                (activity as MainActivity).getLoadingView().visibility = View.GONE
            }
        }, 500)
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.isObserveIndex.value == false) {
            viewModel.getHomeData()
        }
        viewModel.getResponseCase()
    }
    companion object {
        const val IS_UPDATE_AVAILABLE = "IS_UPDATE_AVAILABLE"
    }
}