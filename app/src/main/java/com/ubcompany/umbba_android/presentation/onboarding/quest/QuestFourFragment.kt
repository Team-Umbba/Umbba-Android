package com.ubcompany.umbba_android.presentation.onboarding.quest

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.FragmentQuestFourBinding
import com.ubcompany.umbba_android.util.binding.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuestFourFragment : BindingFragment<FragmentQuestFourBinding>(R.layout.fragment_quest_four) {
    private val viewModel by activityViewModels<QuestViewModel>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewmodel = viewModel

        checkButtonComplete()
    }

    private fun checkButtonComplete() {
        viewModel.isClickedYes.observe(viewLifecycleOwner) {
            viewModel.checkButtonComplete()
            viewModel.clickedChipText.value = binding.btnAnswer1.text.toString()
        }
        viewModel.isClickedNo.observe(viewLifecycleOwner) {
            viewModel.checkButtonComplete()
            viewModel.clickedChipText.value = binding.btnAnswer2.text.toString()
        }
        viewModel.isClickedAmbiguous.observe(viewLifecycleOwner) {
            viewModel.checkButtonComplete()
            viewModel.clickedChipText.value = binding.btnAnswer3.text.toString()
        }
    }
}