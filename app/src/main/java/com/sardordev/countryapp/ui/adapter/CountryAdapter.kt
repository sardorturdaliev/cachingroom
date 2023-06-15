package com.sardordev.countryapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sardordev.countryapp.data.model.CountryData
import com.sardordev.countryapp.databinding.ItemCountryBinding


class CountryAdapter(private val listener:ItemClickListener) : PagingDataAdapter<CountryData, CountryAdapter.VH>(diff) {


    inner class VH(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onbind(countryData: CountryData) {
            binding.tvCountryName.text = countryData.name!!.common
            Glide.with(itemView.context).load(countryData.flags!!.png).into(binding.imgCountry)
            itemView.setOnClickListener {
                listener.clickItem(countryData)
            }
        }
    }

    object diff : DiffUtil.ItemCallback<CountryData>() {
        override fun areItemsTheSame(oldItem: CountryData, newItem: CountryData): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: CountryData, newItem: CountryData): Boolean {
            return oldItem == newItem
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemCountryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        getItem(position)?.let { holder.onbind(it) }
    }


    interface ItemClickListener {
        fun clickItem(countryData: CountryData)
    }

}