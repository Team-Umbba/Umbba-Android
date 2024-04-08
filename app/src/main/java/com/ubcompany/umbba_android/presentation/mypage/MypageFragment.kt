package com.ubcompany.umbba_android.presentation.mypage

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.FragmentMypageBinding
import com.ubcompany.umbba_android.presentation.mypage.viewmodel.MypageViewModel
import com.ubcompany.umbba_android.presentation.setting.SettingActivity
import com.ubcompany.umbba_android.util.binding.BindingFragment
import com.ubcompany.umbba_android.util.setOnSingleClickListener
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MypageFragment : BindingFragment<FragmentMypageBinding>(R.layout.fragment_mypage) {

    private val viewModel by viewModels<MypageViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = viewModel
        observeConnect()
        setClickEvent()
    }

    private fun observeConnect() {
        viewModel.mypageResponse.observe(viewLifecycleOwner) {
            if (it.opponentUsername == null) {
                viewModel.isOpponentNull.value = true
            } else {
                viewModel.isOpponentNull.value = false
            }
        }
    }

    private fun setClickEvent() {
        with(binding) {
            btnSetting.setOnSingleClickListener {
                startActivity(Intent(requireActivity(), SettingActivity::class.java))
            }
            clGetclose.setOnSingleClickListener {
                // 가까워지기
            }
            clRecord.setOnSingleClickListener {
                startActivity(Intent(requireActivity(), RecordActivity::class.java))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getMypage()
    }
}