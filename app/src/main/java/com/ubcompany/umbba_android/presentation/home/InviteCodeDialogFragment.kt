package com.ubcompany.umbba_android.presentation.home

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.dynamiclinks.DynamicLink.AndroidParameters
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
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
        makeInviteLink()
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

    private fun makeInviteLink() {
        val invitationLink = "https://umbba.page.link/umbba?code=$inviteCode"

        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse(invitationLink))
            .setDomainUriPrefix("https://umbba.page.link")
            .setAndroidParameters(
                AndroidParameters.Builder().build()
            )
            .buildShortDynamicLink()

        dynamicLink.addOnSuccessListener { task ->
            val inviteLink = task.shortLink
            if (inviteLink != null) {
                shareMessage(inviteLink)
            }
        }
    }

    private fun shareMessage(inviteLink : Uri) {
        binding.btnSendInvitation.setOnSingleClickListener {
            // 초대 링크를 눌렀을 때 inviteCode가 자동으로 입력되는지 테스트 필요
            val text = getString(R.string.message_title, inviteUserName, inviteCode, inviteLink)
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