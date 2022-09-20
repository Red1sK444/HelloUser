package com.r3d1r4ph.hellouser.presentation.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.r3d1r4ph.hellouser.R
import com.r3d1r4ph.hellouser.databinding.ActivityMainBinding
import com.r3d1r4ph.hellouser.presentation.common.dialog.GreetingDialogFragment
import com.r3d1r4ph.hellouser.presentation.main.model.MainAction
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val viewBinding by viewBinding(ActivityMainBinding::bind, R.id.rootLayout)
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setObserver()
        initView()
    }

    private fun setObserver() {
        viewModel.uiAction.observe(this) { event ->
            event.getContentIfNotHandled()?.let { action ->
                when (action) {
                    MainAction.ShowGreetingDialog -> {
                        GreetingDialogFragment.create()
                            .show(supportFragmentManager, GreetingDialogFragment.TAG)
                    }
                }
            }
        }
    }

    private fun initView() {
        viewBinding.mainHelloButton.setOnClickListener {
            viewModel.onHelloClick()
        }
    }
}