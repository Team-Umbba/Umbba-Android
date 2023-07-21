package com.sopt.umbba_android.presentation.invite

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.local.SharedPreferences.setInviteCodeBoolean
import com.sopt.umbba_android.databinding.ActivityInviteCodeBinding
import com.sopt.umbba_android.domain.entity.User
import com.sopt.umbba_android.presentation.invite.viewmodel.InviteCodeViewModel
import com.sopt.umbba_android.presentation.login.LoginActivity.Companion.DID_USER_CLEAR_INVITE_CODE
import com.sopt.umbba_android.presentation.onboarding.CommunicationActivity
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity
import com.sopt.umbba_android.util.setOnSingleClickListener
import java.util.regex.Pattern

class InviteCodeActivity :
    BindingActivity<ActivityInviteCodeBinding>(R.layout.activity_invite_code),
    View.OnClickListener {

    private val viewModel by viewModels<InviteCodeViewModel> { ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.viewmodel = viewModel

        checkCodeComplete()
        validateInviteCode()
        setFamilyToInviteCode()
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
                        layoutInputCode.error = getString(R.string.input_code_error)
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
                btnNext.isEnabled =
                    layoutInputCode.error.isNullOrEmpty() && etCode.text.toString().isNotEmpty()
            }
        }
    }

    private fun setFamilyToInviteCode() {
        binding.btnNext.setOnSingleClickListener {
            val code = binding.etCode.text.toString()
            Log.e("yeonjin", "초대코드 입력 : $code")
            viewModel.setFamily(code)
            viewModel.isCodeSuccess.observe(this) {
                if (it) {
                    Log.e("yeonjin", "관계 연결 성공")
                    setInviteCodeBoolean(DID_USER_CLEAR_INVITE_CODE, true)
                    goCommunicationActivity()
                } else {
                    Snackbar.make(binding.root, R.string.invalid_invite_code, Snackbar.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun goCommunicationActivity() {
        val userData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("userData", User::class.java)
        } else {
            intent.getParcelableExtra<User>("userData")
        }
        Log.e("yeonjin", "inviteCode parcelable : ${userData?.isReceiver}")
        startActivity(Intent(this, CommunicationActivity::class.java).apply {
            putExtra("userData", userData)
        })
    }

    companion object {
        const val INVITE_CODE_PATTERN = "^[A-Z]{4}-[a-zA-Z0-9]{6}$"
    }
}
