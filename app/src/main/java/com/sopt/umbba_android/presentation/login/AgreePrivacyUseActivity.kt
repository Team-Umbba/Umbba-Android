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
import com.sopt.umbba_android.util.setOnSingleClickListener
import java.util.regex.Pattern

class AgreePrivacyUseActivity :
    BindingActivity<ActivityAgreePrivacyUseBinding>(R.layout.activity_agree_privacy_use) {

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
            btnAllAgreeCheck.setOnSingleClickListener {
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
            btnUp14.setOnSingleClickListener {
                btnUp14.isSelected = !btnUp14.isSelected
                checkAllCircleButton()
            }
            btnAgreeTermsAndCondition.setOnSingleClickListener {
                btnAgreeTermsAndCondition.isSelected = !btnAgreeTermsAndCondition.isSelected
                checkAllCircleButton()
            }
            btnAgreePrivacy.setOnSingleClickListener {
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
            setHyperLinkToTextView(
                getString(R.string.terms_and_condition),
                tvAgreeTermsAndCondition,
                "https://harsh-step-7dd.notion.site/f1a14bf60ed4421f9b3761ef88906adb"
            )
            setLinkTextColorToBlack(tvAgreeTermsAndCondition)
            setLinkBold(tvAgreeTermsAndCondition, 13)
        }
    }

    private fun setHyperLinkAndColorInPrivacyTextView() {
        with(binding) {
            setHyperLinkToTextView(
                getString(R.string.privacy),
                tvAgreePrivacy,
                "https://harsh-step-7dd.notion.site/99fe0f58825d4f87bd3b987fadc623b6?pvs=4"
            )
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
        binding.btnNext.setOnSingleClickListener {
            startActivity(Intent(this, InviteActivity::class.java))
        }
    }
}