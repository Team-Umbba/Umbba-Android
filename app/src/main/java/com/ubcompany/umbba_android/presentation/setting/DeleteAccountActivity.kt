package com.ubcompany.umbba_android.presentation.setting

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.ActivityDeleteAccountBinding
import com.ubcompany.umbba_android.presentation.setting.viewmodel.DeleteAccountViewModel
import com.ubcompany.umbba_android.util.binding.BindingActivity
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteAccountActivity :
    BindingActivity<ActivityDeleteAccountBinding>(R.layout.activity_delete_account),
    View.OnClickListener {
    private val viewModel by viewModels<DeleteAccountViewModel> ()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.clickListener = this
        setClickEvent()
    }

    private fun setClickEvent() {
        with(binding) {
            btnDeleteAccount.setOnSingleClickListener {
                DeleteAccountDialogFragment().apply {
                    show(supportFragmentManager, "DeleteAccountDialog")
                }
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

}