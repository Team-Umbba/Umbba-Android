package com.ubcompany.umbba_android.presentation.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.FragmentListBinding
import com.ubcompany.umbba_android.presentation.list.viewmodel.ListViewModel
import com.ubcompany.umbba_android.presentation.qna.QuestionAnswerActivity
import com.ubcompany.umbba_android.util.ViewModelFactory
import com.ubcompany.umbba_android.util.binding.BindingFragment
import com.ubcompany.umbba_android.util.setOnSingleClickListener

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
        viewModel.listResponseDto.observe(viewLifecycleOwner) {
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
            chip1.setOnSingleClickListener {
                viewModel.getListData(1)
                tvSection.text = chip1.text
                ivImage.load(R.drawable.img_list1)
            }
            chip2.setOnSingleClickListener {
                viewModel.getListData(2)
                tvSection.text = chip2.text
                ivImage.load(R.drawable.img_list2)
            }
            chip3.setOnSingleClickListener {
                viewModel.getListData(3)
                tvSection.text = chip3.text
                ivImage.load(R.drawable.img_list3)
            }
            chip4.setOnSingleClickListener {
                viewModel.getListData(4)
                tvSection.text = chip4.text
                ivImage.load(R.drawable.img_list4)
            }
            chip5.setOnSingleClickListener {
                viewModel.getListData(5)
                tvSection.text = chip5.text
                ivImage.load(R.drawable.img_list5)
            }
        }
    }

}