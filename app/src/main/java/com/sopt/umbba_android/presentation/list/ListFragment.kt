package com.sopt.umbba_android.presentation.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentListBinding
import com.sopt.umbba_android.presentation.list.viewmodel.ListViewModel
import com.sopt.umbba_android.presentation.qna.QuestionAnswerActivity
import com.sopt.umbba_android.util.ViewModelFactory
import com.sopt.umbba_android.util.binding.BindingFragment

class ListFragment : BindingFragment<FragmentListBinding>(R.layout.fragment_list) {
    private val viewModel: ListViewModel by viewModels { ViewModelFactory(requireActivity()) }
    private lateinit var listQuestionAdapter: ListQuestionAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setChipClickEvent()
        initAdapter()
        observeData()
    }

    private fun observeData() {
        viewModel.listResponseDto.observe(requireActivity()) {
            listQuestionAdapter.submitList(it)
        }
    }

    private fun initAdapter() {
        listQuestionAdapter = ListQuestionAdapter {
            Intent(Intent(requireContext(), QuestionAnswerActivity::class.java)).apply {
                putExtra("questionId", it.qnaId)
                startActivity(this)
            }
        }
        binding.rvQuestionList.adapter = listQuestionAdapter
    }

    private fun setChipClickEvent() {
        with(binding) {
            chip1.setOnClickListener {
                viewModel.getListData(1)
                tvSection.text = chip1.text
                ivImage.load(R.drawable.bg_list_img1)
            }
            chip2.setOnClickListener {
                viewModel.getListData(2)
                tvSection.text = chip2.text
                ivImage.load(R.drawable.bg_list_img2)
            }
            chip3.setOnClickListener {
                viewModel.getListData(3)
                tvSection.text = chip3.text
                ivImage.load(R.drawable.bg_list_img3)
            }
            chip4.setOnClickListener {
                viewModel.getListData(4)
                tvSection.text = chip4.text
                ivImage.load(R.drawable.bg_list_img4)
            }
            chip5.setOnClickListener {
                viewModel.getListData(5)
                tvSection.text = chip5.text
                ivImage.load(R.drawable.bg_list_img5)
            }
        }
    }

}