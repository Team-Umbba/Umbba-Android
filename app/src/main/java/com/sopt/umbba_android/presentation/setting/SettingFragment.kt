package com.sopt.umbba_android.presentation.setting

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentSettingBinding
import com.sopt.umbba_android.util.binding.BindingFragment

class SettingFragment:BindingFragment<FragmentSettingBinding>(R.layout.fragment_setting) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickEvent()
    }
    private fun setClickEvent(){
        with(binding){
            clManageAccount.setOnClickListener {
                startActivity(Intent(requireActivity(),ManageAccountActivity::class.java))
            }
            clAboutUmbba.setOnClickListener{
                TODO("엄빠소개 노션으로 이동하도록 만들기. Uri 연결하기")
            }
            clTos.setOnClickListener {
                TODO("이용약관 노션으로 이동하도록 만들기. Uri 연결하기")
            }
            clNotice.setOnClickListener {
                TODO("개인정보처리방침 노션으로 이동하도록 만들기. Uri 연결하기")
            }
        }
    }

}
