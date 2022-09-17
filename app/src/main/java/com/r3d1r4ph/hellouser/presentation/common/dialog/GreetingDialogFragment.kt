package com.r3d1r4ph.hellouser.presentation.common.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.databinding.DialogGreetingBinding

class GreetingDialogFragment : DialogFragment(R.layout.dialog_greeting) {

    companion object {
        val TAG: String = GreetingDialogFragment::class.java.simpleName

        fun create() = GreetingDialogFragment().apply {
            isCancelable = false
        }
    }

    private val viewBinding by viewBinding(DialogGreetingBinding::bind, R.id.rootLayout)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppMaterialDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        viewBinding.greetingContinueButton.setOnClickListener {
            dismiss()
        }
    }
}