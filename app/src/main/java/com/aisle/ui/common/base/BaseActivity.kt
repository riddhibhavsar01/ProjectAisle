package com.aisle.ui.common.base

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.aisle.data.local.pref.Preference
import com.aisle.ui.common.MY_UPDATE_REQUEST_CODE
import com.aisle.utils.extention.hideKeyboard
import javax.inject.Inject


abstract class BaseActivity<B : ViewBinding> : AppCompatActivity(), View.OnClickListener {

    @Inject
    lateinit var preference: Preference
    protected lateinit var binding: B
    abstract fun onViewBinding(): B
    abstract fun initView()
    abstract fun initListener()
    abstract fun initObserver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = onViewBinding()
        setContentView(binding.root)
        initView()
        initListener()
        initObserver()
    }

    override fun onClick(v: View?) {
        hideKeyboard(v)
    }


}