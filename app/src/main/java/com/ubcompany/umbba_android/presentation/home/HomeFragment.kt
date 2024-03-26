package com.ubcompany.umbba_android.presentation.home

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import coil.load
import com.skydoves.balloon.ArrowOrientation
import com.skydoves.balloon.Balloon
import com.skydoves.balloon.BalloonSizeSpec
import com.skydoves.balloon.overlay.BalloonOverlayRoundRect
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
    private var isShowedInviteCodeCoachMark = false
    private var isShowedTutorialCoachMark = false

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

    private fun observeData() {
        viewModel.homeData.observe(viewLifecycleOwner) {
            setBackgroundImage(it.section)
            if (it.index == IS_AFTER_7DAYS_INDEX && viewModel.isShowedEndingPage()) {
                viewModel.setStateObserveIndex()
                startForResult.launch(Intent(requireActivity(), EndingActivity::class.java))
            }
        }
        viewModel.responseCaseData.observe(viewLifecycleOwner) {
            setBtnClickEvent(it)
            checkBtnCoachMark(it)
        }
    }


    private fun setBtnClickEvent(responseCaseDto: HomeCaseResponseDto.HomeCaseData) {
        binding.btnAnswer.setOnSingleClickListener {
            viewModel.getResponseCase()
            when (responseCaseDto.responseCase) {
                MATCHED_OPPONENT -> {
                    startActivity(
                        Intent(
                            requireActivity(),
                            QuestionAnswerActivity::class.java
                        )
                    )
                }

                REQUIRE_INVITE_CODE -> {
                    showInviteDialog(responseCaseDto.inviteUserName!!, responseCaseDto.inviteCode!!)
                }

                DELETE_OPPONENT -> showDeleteOpponentDialog()

                REQUIRE_TUTORIAL -> {
                    SharedPreferences.setTutorialBoolean(DID_TUTORIAL,false)
                    startActivity(Intent(requireActivity(), QuestionAnswerActivity::class.java))
                }
            }
        }
    }

    private fun checkBtnCoachMark(responseCaseDto: HomeCaseResponseDto.HomeCaseData) {
        if (responseCaseDto.responseCase == REQUIRE_TUTORIAL) {
            if (!isShowedTutorialCoachMark) {
                showTutorialClickCoachMark()
                isShowedTutorialCoachMark = true
            }
        }

        if (responseCaseDto.responseCase == REQUIRE_INVITE_CODE) {
            if (!isShowedInviteCodeCoachMark) {
                showInviteOpponentCoachMark()
                isShowedInviteCodeCoachMark = true
            }
        }
    }

    private fun showTutorialClickCoachMark() {
        createBalloon("클릭하여 오늘의 질문을 확인하자")
    }

    private fun showInviteOpponentCoachMark() {
        createBalloon("상대를 초대하고 답장을 받아보자")
    }

    private fun createBalloon(text: String) {
        val balloon = Balloon.Builder(requireContext())
            .setWidth(BalloonSizeSpec.WRAP)
            .setHeight(BalloonSizeSpec.WRAP)
            .setTextColorResource(R.color.grey_900)
            .setBackgroundColorResource(R.color.white_500)
            .setTextGravity(Gravity.CENTER)
            .setText(text)
            .setTextSize(16f)
            .setPaddingHorizontal(16)
            .setPaddingVertical(6)
            .setCornerRadius(4f)
            .setIsVisibleArrow(true)
            .setArrowSize(12)
            .setArrowPosition(0.5f)
            .setIsVisibleOverlay(true)
            .setLifecycleOwner(viewLifecycleOwner)
            .setArrowOrientation(ArrowOrientation.BOTTOM)
            .setOverlayColorResource(R.color.black_opacity50)
            .setOverlayShape(BalloonOverlayRoundRect(80f, 80f))
            .setMarginBottom(8)
            .setDismissWhenOverlayClicked(true)
            .build()
        balloon.showAlignTop(binding.btnAnswer)
    }

    private fun showInviteDialog(inviteUserName: String, inviteCode: String) {
        InviteCodeDialogFragment(inviteUserName, inviteCode).show(
            requireActivity().supportFragmentManager,
            "InviteCodeDialogFragment"
        )
    }

    private fun showDeleteOpponentDialog() {
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
        if (SharedPreferences.getUpdateAvailableBoolean(IS_UPDATE_AVAILABLE)) {
            showUpdateDialog()
        }
    }

    private fun setBackgroundImage(section: String) {
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
        }, DELAY_MILLIS)

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
        const val IS_AFTER_7DAYS_INDEX = 8
        const val DELAY_MILLIS = 500L
        const val MATCHED_OPPONENT = 1
        const val REQUIRE_INVITE_CODE = 2
        const val DELETE_OPPONENT = 3
        const val REQUIRE_TUTORIAL = 4
        const val DID_TUTORIAL = "DID_TUTORIAL"
    }
}