package com.aisle.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aisle.R
import com.aisle.databinding.FragmentHomeBinding
import com.aisle.ui.common.base.BaseFragment
import com.aisle.utils.extention.handleListApiView
import com.aisle.utils.extention.navigate
import com.aisle.utils.extention.observeNotNull
import com.aisle.utils.glide.GlideApp
import com.aisle.utils.glide.GlideRequests
import com.aisle.utils.glide.loadUrl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var glideRequests: GlideRequests
    private lateinit var homeAdapter: HomeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        glideRequests = GlideApp.with(this)
        homeAdapter = HomeAdapter(glideRequests = glideRequests, onClickListener = this)
        homeViewModel.getUserProfiles()
    }

    override fun onViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater, container, false)
    }

    override fun initView() {
        binding.rootLayout.recyclerView = binding.rvConnections
        binding.rootLayout.swipeRefreshLayout = binding.swipeRefreshLayout
        binding.rootLayout.recyclerView?.adapter = homeAdapter

        binding.rootLayout.setOnSwipeRefreshLayout {
            homeViewModel.getUserProfiles(isRefresh = true)
        }
        binding.rootLayout.setOnLoadMore {
            homeViewModel.getUserProfiles(isLoadMore = true)
        }
    }

    override fun initListener() {
       binding.btnUpgrade.setOnClickListener(this)
    }


    override fun initObserver() {
        observeNotNull(homeViewModel.apiState) {
            it.handleListApiView(binding.rootLayout, onClickListener = { homeViewModel.getUserProfiles() }) {
                glideRequests.loadUrl(binding.ivUserProfile, it?.invites?.profiles?.get(0)?.photos?.get(0)?.photo
                    ?: "")
                binding.tvUserName.text = it?.invites?.profiles?.get(0)?.generalInformation?.firstName ?: "User Name"
                homeAdapter.list.addAll(it?.likes?.profiles ?: arrayListOf())
            }
        }

    }

    override fun onClick(view: View?) {
        super.onClick(view)
        when (view?.id) {
         R.id.btnUpgrade ->{
             // Click on Upgrade to reset
             preference.clear()
             navigate(R.id.action_homeFragment_to_loginFragment)
         }
        }
    }


}