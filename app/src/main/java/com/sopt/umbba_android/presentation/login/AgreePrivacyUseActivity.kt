package com.sopt.umbba_android.presentation.login

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.text.util.Linkify
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityAgreePrivacyUseBinding
import com.sopt.umbba_android.presentation.invite.InviteActivity
import com.sopt.umbba_android.util.binding.BindingActivity
import java.util.regex.Pattern

class AgreePrivacyUseActivity : BindingActivity<ActivityAgreePrivacyUseBinding>(R.layout.activity_agree_privacy_use) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        clickAllAgreeButton()
        clickCircleButton()
        setHyperLinkAndColorInTermsTextView()
        setHyperLinkAndColorInPrivacyTextView()
        goInviteActivity()
    }

    private fun clickAllAgreeButton() {
        with(binding) {
            btnAllAgreeCheck.setOnClickListener {
                btnAllAgreeCheck.isSelected = !btnAllAgreeCheck.isSelected
                checkAllAgreeButton()
            }
        }
    }

    private fun checkAllAgreeButton() {
        with(binding) {
            if (btnAllAgreeCheck.isSelected) {
                btnUp14.isSelected = true
                btnAgreeTermsAndCondition.isSelected = true
                btnAgreePrivacy.isSelected = true
                btnNext.isEnabled = true
            } else {
                btnUp14.isSelected = false
                btnAgreeTermsAndCondition.isSelected = false
                btnAgreePrivacy.isSelected = false
                btnNext.isEnabled = false
            }
        }
    }

    private fun clickCircleButton() {
        with(binding) {
            btnUp14.setOnClickListener {
                btnUp14.isSelected = !btnUp14.isSelected
                checkAllCircleButton()
            }
            btnAgreeTermsAndCondition.setOnClickListener {
                btnAgreeTermsAndCondition.isSelected = !btnAgreeTermsAndCondition.isSelected
                checkAllCircleButton()
            }
            btnAgreePrivacy.setOnClickListener {
                btnAgreePrivacy.isSelected = !btnAgreePrivacy.isSelected
                checkAllCircleButton()
            }
        }
    }

    private fun checkAllCircleButton() {
        with(binding) {
            if (btnUp14.isSelected && btnAgreeTermsAndCondition.isSelected && btnAgreePrivacy.isSelected) {
                btnAllAgreeCheck.isSelected = true
                btnNext.isEnabled = true
            } else {
                btnAllAgreeCheck.isSelected = false
                btnNext.isEnabled = false
            }
        }
    }

    private fun setHyperLinkAndColorInTermsTextView() {
        with(binding) {
            setHyperLinkToTextView("서비스 이용약관", tvAgreeTermsAndCondition, "https://www.notion.so/f1a14bf60ed4421f9b3761ef88906adb?pvs=4")
            setLinkTextColorToBlack(tvAgreeTermsAndCondition)
            setLinkBold(tvAgreeTermsAndCondition, 13)
        }
    }

    private fun setHyperLinkAndColorInPrivacyTextView() {
        with(binding) {
            setHyperLinkToTextView("개인정보 수집 및 이용", tvAgreePrivacy, "https://www.notion.so/99fe0f58825d4f87bd3b987fadc623b6?pvs=4")
            setLinkTextColorToBlack(tvAgreePrivacy)
            setLinkBold(tvAgreePrivacy, 17)
        }
    }

    private fun setHyperLinkToTextView(text: String, view: TextView, uri: String) {
        val transform = Linkify.TransformFilter { match, url -> "" }
        val pattern = Pattern.compile(text)

        Linkify.addLinks(view, pattern, uri, null, transform)
    }

    private fun setLinkTextColorToBlack(view: TextView) {
        view.setLinkTextColor(ContextCompat.getColor(this, R.color.umbba_black))
    }

    private fun setLinkBold(view: TextView, end: Int) {
        val sentence = SpannableStringBuilder(view.text)
        sentence.setSpan(StyleSpan(Typeface.BOLD), 5, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.text = sentence
    }

    private fun goInviteActivity() {
        binding.btnNext.setOnClickListener {
            startActivity(Intent(this, InviteActivity::class.java))
        }
    }
}