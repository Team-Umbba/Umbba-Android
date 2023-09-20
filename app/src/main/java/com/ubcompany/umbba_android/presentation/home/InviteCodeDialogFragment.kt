package com.ubcompany.umbba_android.presentation.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.FragmentInviteCodeDialogBinding
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InviteCodeDialogFragment(private val inviteUserName: String, private val inviteCode: String) :
    DialogFragment() {

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
        shareMessage(inviteUserName, inviteCode)
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
            Snackbar.make(binding.root, R.string.copy_invite_code_snackbar, Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun shareMessage(inviteUserName: String, inviteCode: String) {
        binding.btnSendInvitation.setOnSingleClickListener {
            // 카카오톡에서 동적링크가 활성화 되는지 안드로이드 폰에서 테스트 필요
            // 만약 활성화가 안될 경우 uri를 따로 지정한 후 putExtra 해줘야 함
            // 현재 ShareSheet, 썸네일 이미지는 잘 되는 중
            val text = getString(R.string.message_title, inviteUserName, inviteCode, inviteCode)
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, text)
            }
            startActivity(Intent.createChooser(intent, text))
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