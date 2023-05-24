package com.aisle.ui.home

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aisle.R
import com.aisle.databinding.ListItemBinding
import com.aisle.ui.common.base.BaseAdapter
import com.aisle.ui.common.model.Profile
import com.aisle.utils.extention.*
import com.aisle.utils.glide.GlideRequests
import com.aisle.utils.glide.loadUrl

class HomeAdapter(
    val list: ArrayList<Profile?> = arrayListOf(),
    private val glideRequests: GlideRequests,
    private val onClickListener: View.OnClickListener
) : BaseAdapter<Profile>(list) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType != LIST_ITEM_PROGRESS) {
            return ProfileViewHolder(ListItemBinding.bind(parent.inflate(R.layout.list_item)))
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        when (holder) {
            is ProfileViewHolder -> {
                holder.bind(list[position])
            }
        }
    }

    inner class ProfileViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {


        fun bind(profile: Profile?) = with(profile) {
            binding.tvInterestName.text = profile?.firstName ?: "Name"
            glideRequests.loadUrl(binding.ivUserProfile, this?.avatar ?: "")

        }
    }
}