package com.sardordev.countryapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sardordev.countryapp.data.dt.enity.CountryEntity
import com.sardordev.countryapp.data.model.CountryData
import com.sardordev.countryapp.databinding.ItemCountryBinding


class CountryCacheAdapter : ListAdapter<CountryEntity, CountryCacheAdapter.VH>(diff) {

    inner class VH(val binding: ItemCountryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onbind(countryData: CountryEntity) {
            binding.tvCountryName.text = countryData.countryName
            Glide.with(itemView.context).load(countryData.imgurl).into(binding.imgCountry)
        }
    }

    object diff : DiffUtil.ItemCallback<CountryEntity>() {
        override fun areItemsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
            return oldItem.countryName == newItem.countryName
        }
        override fun areContentsTheSame(oldItem: CountryEntity, newItem: CountryEntity): Boolean {
            return oldItem.equals(newItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VH(
        ItemCountryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onbind(getItem(position))
    }
}