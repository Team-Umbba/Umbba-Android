package com.sopt.umbba_android.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentHomeBinding
import com.sopt.umbba_android.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBackground()
    }
    private fun setBackground(){
        with(binding){
            ivBackground.load("https://i.ibb.co/sRV9Vr4/iv-maru.jpg")
        }
    }
}