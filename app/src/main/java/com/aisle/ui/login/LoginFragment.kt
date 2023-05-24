package com.aisle.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aisle.R
import com.aisle.databinding.FragmentHomeBinding
import com.aisle.databinding.FragmentLoginBinding
import com.aisle.ui.common.base.BaseFragment
import com.aisle.utils.extention.handleApiView
import com.aisle.utils.extention.handleListApiView
import com.aisle.utils.extention.isEmpty
import com.aisle.utils.extention.navigate
import com.aisle.utils.extention.observeNotNull
import com.aisle.utils.extention.setupCountryCodePicker
import com.aisle.utils.extention.showSnackBar
import com.aisle.utils.glide.GlideApp
import com.aisle.utils.glide.GlideRequests
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(inflater, container, false)
     
    }

    // set country code and mobile number added
    private fun setData() {
       binding.etMobileNumber.setText(preference.getMobileNumber())
       binding.tvCountryCode.text = if(!preference.getCountryCode().isEmpty()) preference.getCountryCode() else "+1"
    }

    override fun initView() {
        if (preference.isLogin()){
            navigate(R.id.action_loginFragment_to_homeFragment)
        }
        else {
            setData()
        }
    }

    override fun initListener() {
        binding.btnContinue.setOnClickListener(this)
        binding.tvCountryCode.setOnClickListener(this)

    }

    override fun initObserver() {
        // observer for login api response
        observeNotNull(loginViewModel.apiState) {
            it.handleApiView(binding.rootLayout) {
              if (it?.status != null) {
                      navigate(R.id.action_loginFragment_to_otpFragemnt)
                      preference.setMobileNumber(binding.etMobileNumber.text.toString())
                      preference.setCountryCode(binding.tvCountryCode.text.toString())
              }
            }
        }
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            R.id.btnContinue -> {
              if(validateLogin()) {
                  loginViewModel.sendMobileLogin(binding.tvCountryCode.text.toString()+binding.etMobileNumber.text.toString())
              }
            }
            R.id.tvCountryCode -> {
                binding.tvCountryCode.setupCountryCodePicker {
                    binding.tvCountryCode.requestFocus()
                }
            }
        }
    }
    // validations for user input mobile number and country code
    private fun validateLogin(): Boolean {
      if (binding.tvCountryCode.text.isNullOrEmpty()){
          showSnackBar(binding.rootLayout,getString(R.string.lbl_select_country_code))
      }else if(binding.etMobileNumber.text.isNullOrEmpty()){
          showSnackBar(binding.rootLayout,getString(R.string.lbl_enter_mobile_number))
      }else if(binding.etMobileNumber.text.toString().length<10){
          showSnackBar(binding.rootLayout,getString(R.string.lbl_enter_valid_mobile_number))
      }else{
         return true
      }
       return false
    }


}