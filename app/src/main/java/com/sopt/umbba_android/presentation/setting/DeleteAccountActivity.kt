package com.sopt.umbba_android.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.ActivityDeleteAccountBinding
import com.sopt.umbba_android.presentation.login.LoginActivity
import com.sopt.umbba_android.presentation.setting.viewmodel.DeleteAccountViewModel
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingActivity

class DeleteAccountActivity :
    BindingActivity<ActivityDeleteAccountBinding>(R.layout.activity_delete_account),
    View.OnClickListener, DeleteAccountDialogFragment.OnSignOutListener {
    private val viewModel: DeleteAccountViewModel by viewModels { ViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        setClickEvent()
    }

    private fun setClickEvent() {
        with(binding) {
            btnDeleteAccount.setOnClickListener {
                DeleteAccountDialogFragment().apply{
                    setOnSignOutListener(this@DeleteAccountActivity)
                    show(supportFragmentManager, "DeleteAccountDialog")
                }
            }
        }
    }

    private fun observeResponseStatus() {
        viewModel.responseStatus.observe(this@DeleteAccountActivity) {
            Log.e("hyeon", "responseStatus값은" + viewModel.responseStatus.value)
            if (it == 200) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.iv_basic_back -> {
                finish()
            }
        }
    }

    override fun onSignOutConfirmed() {
        // 여기에 로그아웃 후 할 로직 작성하면될듯
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}