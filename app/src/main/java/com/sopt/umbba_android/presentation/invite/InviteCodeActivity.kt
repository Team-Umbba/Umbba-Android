package com.sopt.umbba_android.presentation.invite

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityInviteCodeBinding
import com.sopt.umbba_android.presentation.invite.viewmodel.InviteCodeViewModel
import com.sopt.umbba_android.presentation.onboarding.CommunicationActivity
import com.sopt.umbba_android.util.binding.BindingActivity
import java.util.regex.Pattern

class InviteCodeActivity :
    BindingActivity<ActivityInviteCodeBinding>(R.layout.activity_invite_code),
    View.OnClickListener {

    private val viewModel by viewModels<InviteCodeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.viewmodel = viewModel

        checkCodeComplete()
        validateInviteCode()
        goCommunicationActivity()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun validateInviteCode() {
        with(binding) {
            etCode.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    val namePattern = Pattern.compile(INVITE_CODE_PATTERN)
                    if (!etCode.text?.matches(namePattern.toRegex())!!) {
                        layoutInputCode.error = "*올바른 초대코드 형식이 아닙니다."
                    } else {
                        layoutInputCode.error = null
                    }
                }
                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun checkCodeComplete() {
        viewModel.code.observe(this) {
            viewModel.checkCodeComplete()
        }
        viewModel.isCodeValidate.observe(this) {
            with(binding) {
                btnNext.isEnabled = layoutInputCode.error.isNullOrEmpty() && etCode.text.toString().isNotEmpty()
            }
        }
    }

    private fun goCommunicationActivity() {
        binding.btnNext.setOnClickListener {
            //초대하는측인지 초대받는측인지 보내기
            startActivity(Intent(this, CommunicationActivity::class.java))
        }
    }

    companion object {
        const val INVITE_CODE_PATTERN = "^[A-Z]{4}-[a-zA-Z0-9]{6}$"
    }
}
