package com.sopt.umbba_android.presentation.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import com.sopt.umbba_android.R
import com.sopt.umbba_android.data.model.response.HomeResponseDto
import com.sopt.umbba_android.databinding.FragmentHomeBinding
import com.sopt.umbba_android.presentation.qna.QuestionAnswerActivity
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingFragment

class HomeFragment : BindingFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private val viewModel: HomeViewModel by viewModels { ViewModelFactory(requireActivity()) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // viewModel.getHomeData()
       // observeData()
        setClickEvent()
    }

    private fun setClickEvent() {
        with(binding) {
            btnAnswer.setOnClickListener {
                startActivity(Intent(requireActivity(), QuestionAnswerActivity::class.java))
            }
        }
    }

    private fun observeData() {
        viewModel.homeData.observe(requireActivity()) {
            setData(it)
            setBackground(it.section)
        }
    }

    @SuppressLint("StringFormatMatches")
    private fun setData(data: HomeResponseDto.HomeData) {
        with(binding) {
            tvTitle.text = getString(R.string.main_topic, data.index, data.topic)
        }
    }

    private fun setBackground(section: String) {
        binding.ivBackground.load(
            when (section) {
                "어린시절" -> R.drawable.bg_home1
                "학창시절" -> R.drawable.bg_home2
                "청춘시절" -> R.drawable.bg_home3
                "연애시절" -> R.drawable.bg_home4
                else -> R.drawable.bg_home5
            }
        )
    }
}