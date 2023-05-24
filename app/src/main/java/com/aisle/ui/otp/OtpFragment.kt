package com.aisle.ui.otp

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aisle.R
import com.aisle.databinding.FragmentHomeBinding
import com.aisle.databinding.FragmentOtpBinding
import com.aisle.ui.common.base.BaseFragment
import com.aisle.utils.extention.handleApiView
import com.aisle.utils.extention.navigate
import com.aisle.utils.extention.observeNotNull
import com.aisle.utils.extention.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import java.util.Timer

@AndroidEntryPoint
class OtpFragment : BaseFragment<FragmentOtpBinding>() {

    private val otpViewModel: OtpViewModel by viewModels()
    lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startTimer()
    }
   // start timer from when otp screen visible
    private fun startTimer() {
        timer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                updateTextUI(millisUntilFinished)
            }

            override fun onFinish() {
                binding.tvResendTimer.text = getString(R.string.lbl_resend_otp)
            }
        }
        timer.start()
    }

    override fun onViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentOtpBinding {
        return FragmentOtpBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        binding.tvMobileNumber.text = preference.getCountryCode()+" "+preference.getMobileNumber()
    }

    override fun initListener() {
        binding.btnContinue.setOnClickListener(this)
        binding.ivEdit.setOnClickListener(this)
    }

    override fun initObserver() {

        observeNotNull(otpViewModel.apiState) {
            it.handleApiView(binding.rootLayout) {
                if (it?.token != null) {
                    preference.setToken(it.token ?: "")
                    preference.setLogin()
                    timer.cancel()
                    navigate(R.id.action_otpFragment_to_homeFragment)

                } else {
                    showSnackBar(
                        binding.rootLayout,
                        getString(R.string.lbl_enter_valid_otp)
                    )
                }


            }
        }
    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
            R.id.btnContinue -> {
                if (validateOtp()) {
                    otpViewModel.verifyOtp(
                       preference.getCountryCode()+preference.getMobileNumber().replace(" ", ""),
                        binding.tvOtp.text.toString()
                    )
                }
            }

            R.id.ivEdit -> {
                timer.cancel()
                navigate(R.id.action_otpFragment_to_loginFragment)

            }
        }
    }

    // validations for input otp
    private fun validateOtp(): Boolean {

        if (binding.tvOtp.text.isNullOrEmpty()) {
            showSnackBar(binding.rootLayout, getString(R.string.lbl_user_enter_otp))
        }else if(binding.tvOtp.text.toString().length<4){
            showSnackBar(binding.rootLayout, getString(R.string.lbl_enter_valid_otp))
        } else {
            return true
        }
        return false

    }

    // resend timer text update
    private fun updateTextUI(milliseconds: Long) {
        val minute = (milliseconds / 1000) / 60
        val seconds = (milliseconds / 1000) % 60

        binding.tvResendTimer.text = "$minute:$seconds"
    }
}