package com.sopt.umbba_android.presentation.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sopt.umbba_android.R
import com.sopt.umbba_android.databinding.FragmentQuestOneBinding
import com.sopt.umbba_android.util.binding.BindingFragment

class QuestSubFragment : BindingFragment<FragmentQuestOneBinding> (R.layout.fragment_quest_one) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}