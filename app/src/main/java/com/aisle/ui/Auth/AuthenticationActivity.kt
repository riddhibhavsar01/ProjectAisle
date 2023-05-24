package com.aisle.ui.Auth

import com.aisle.databinding.ActivityHomeBinding
import com.aisle.ui.common.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthenticationActivity : BaseActivity<ActivityHomeBinding>() {

    override fun onViewBinding(): ActivityHomeBinding {
        return ActivityHomeBinding.inflate(layoutInflater)
    }



    override fun initView() {
        // Binding object with view
        binding.navHostFragment

    }

    override fun initListener() {
    }

    override fun initObserver() {
    }

    override fun onResume() {
        super.onResume()

    }


}
