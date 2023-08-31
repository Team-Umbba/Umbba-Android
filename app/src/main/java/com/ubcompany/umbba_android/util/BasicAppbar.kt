package com.ubcompany.umbba_android.util

import android.os.Bundle
import android.view.View
import com.ubcompany.umbba_android.R
import com.ubcompany.umbba_android.databinding.BasicAppbarBinding
import com.ubcompany.umbba_android.util.binding.BindingFragment

class BasicAppbar : BindingFragment<BasicAppbarBinding>(R.layout.basic_appbar) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}