package com.ubcompany.umbba_android.presentation.invite

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.data.local.SharedPreferences.setInviteCodeBoolean
import com.ubcompany.umbba_android.databinding.ActivityInviteCodeBinding
import com.ubcompany.umbba_android.domain.entity.User
import com.ubcompany.umbba_android.presentation.invite.viewmodel.InviteCodeViewModel
import com.ubcompany.umbba_android.presentation.login.LoginActivity.Companion.DID_USER_CLEAR_INVITE_CODE
import com.ubcompany.umbba_android.presentation.onboarding.CommunicationActivity
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern

@AndroidEntryPoint
class InviteCodeActivity :
    BindingActivity<ActivityInviteCodeBinding>(R.layout.activity_invite_code),
    View.OnClickListener {

    private val viewModel by viewModels<InviteCodeViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        binding.viewmodel = viewModel

        initInviteCode()
        checkCodeComplete()
        validateInviteCode()
        setFamilyToInviteCode()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> finish()
        }
    }

    private fun initInviteCode() {
        val inviteCode = intent.getStringExtra("inviteCode")
        if (inviteCode != null) {
            viewModel.code.value = inviteCode
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
            viewModel.setFamily(code)
            viewModel.isCodeSuccess.observe(this) {
                if (it) {
                    setInviteCodeBoolean(DID_USER_CLEAR_INVITE_CODE, true)
                    goCommunicationActivity()
                } else {
                    Snackbar.make(binding.root, R.string.invalid_invite_code, Snackbar.LENGTH_SHORT)
                        .show()
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
        startActivity(Intent(this, CommunicationActivity::class.java).apply {
            putExtra("userData", userData)
        })
    }

    companion object {
        const val INVITE_CODE_PATTERN = "^[A-Z]{4}-[a-zA-Z0-9]{6}$"
    }
}
