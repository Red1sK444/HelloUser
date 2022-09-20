package com.r3d1r4ph.hellouser.presentation.common.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.databinding.DialogGreetingBinding
import com.r3d1r4ph.hellouser.presentation.common.dialog.model.GreetingAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GreetingDialogFragment : DialogFragment(R.layout.dialog_greeting) {

    companion object {
        val TAG: String = GreetingDialogFragment::class.java.simpleName

        fun create() = GreetingDialogFragment().apply {
            isCancelable = false
        }
    }

    private val viewBinding by viewBinding(DialogGreetingBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<GreetingViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppMaterialDialog)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObservers()
        initView()
    }

    private fun setObservers() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) { uiState ->
            viewBinding.greetingMessageTextView.text =
                getString(R.string.hello_name, uiState.userName)
        }
        uiAction.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    is GreetingAction.DismissDialog -> dismiss()
                }
            }
        }
    }

    private fun initView() = with(viewBinding) {
        greetingContinueButton.setOnClickListener {
            viewModel.onContinueClick()
        }
    }
}