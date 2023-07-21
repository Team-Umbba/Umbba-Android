package com.sopt.umbba_android.presentation.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.kakao.sdk.share.ShareClient
import com.kakao.sdk.template.model.Button
import com.kakao.sdk.template.model.Content
import com.kakao.sdk.template.model.FeedTemplate
import com.kakao.sdk.template.model.Link
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentInviteCodeDialogBinding
import com.sopt.umbba_android.util.setOnSingleClickListener

class InviteCodeDialogFragment(private val inviteUserName: String, private val inviteCode: String) : DialogFragment() {

    private var _binding: FragmentInviteCodeDialogBinding? = null
    private val binding get() = requireNotNull(_binding) { "InviteCodeDialogFragment is null" }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentInviteCodeDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        copyInviteCode()
        closeDialog()
        setBackgroundDesign()
        setInviteCodeText(inviteCode)
        sendInviteCodeWithKakao(inviteUserName, inviteCode)
    }

    private fun closeDialog() {
        binding.btnClose.setOnSingleClickListener {
            dismiss()
        }
    }

    private fun setBackgroundDesign() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun setInviteCodeText(inviteCode: String) {
        binding.tvInviteCode.text = inviteCode
    }

    private fun copyInviteCode() {
        binding.clCopyInviteCode.setOnSingleClickListener {
            val clipboard: ClipboardManager =
                requireActivity().getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("label", binding.tvInviteCode.text)
            clipboard.setPrimaryClip(clip)
            Snackbar.make(binding.root, R.string.copy_invite_code_snackbar, Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendInviteCodeWithKakao(inviteUserName: String, inviteCode: String) {
        binding.btnSendInvitation.setOnSingleClickListener {
            val defaultFeed = FeedTemplate(
                content = Content(
                    title = "${inviteUserName}으로부터 초대가 왔어요.\n초대 코드 : $inviteCode",
                    description = "과거로 떠나 함께 추억을 나누고,\n공감대를 형성해보세요.",
                    imageUrl =  "https://github.com/Team-Umbba/Umbba-iOS/assets/75068759/64ba7265-9148-4f06-8235-de5f4030e92f",
                    link = Link(
                        webUrl = "https://developers.kakao.com",
                        mobileWebUrl = "https://developers.kakao.com"
                    )
                ),
                buttons = listOf(
                    Button(
                        "초대 받기",
                        Link(
                            androidExecutionParams = mapOf("key1" to "value1", "key2" to "value2"),
                            iosExecutionParams = mapOf("key1" to "value1", "key2" to "value2")
                        )
                    )
                )
            )

            if (ShareClient.instance.isKakaoTalkSharingAvailable(requireContext())) {
                ShareClient.instance.shareDefault(requireContext(), defaultFeed) { sharingResult, error ->
                    if (error != null) {
                        Log.e("yeonjin", "카카오톡 공유 실패", error)
                    } else if (sharingResult != null) {
                        Log.e("yeonjin", "카카오톡 공유 성공 ${sharingResult.intent}")
                        startActivity(sharingResult.intent)

                        Log.w("yeonjin", "Warning Msg: ${sharingResult.warningMsg}")
                        Log.w("yeonjin", "Argument Msg: ${sharingResult.argumentMsg}")
                    }
                }
            } else {
                Snackbar.make(binding.root, R.string.install_kakaotalk, Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}