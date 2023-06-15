package com.sardordev.countryapp.ui

import android.nfc.tech.MifareUltralight.PAGE_SIZE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.sardordev.countryapp.R
import com.sardordev.countryapp.data.dt.db.dao.CountryDao
import com.sardordev.countryapp.data.dt.db.CountryDatabase
import com.sardordev.countryapp.data.dt.enity.CountryEntity
import com.sardordev.countryapp.data.model.CountryData
import com.sardordev.countryapp.databinding.CustomBottomSheetBinding
import com.sardordev.countryapp.databinding.FragmentMainScreenBinding
import com.sardordev.countryapp.ui.adapter.CountryAdapter
import com.sardordev.countryapp.utils.UiEvent
import com.sardordev.countryapp.utils.displayToast
import com.sardordev.countryapp.utils.pagination.MainLoadStateAdapter
import com.sardordev.countryapp.utils.pagination.MyPagingSource
import com.sardordev.countryapp.viewmodel.CounrtyViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class MainScreen : Fragment(), CountryAdapter.ItemClickListener {
    private val binding by lazy { FragmentMainScreenBinding.inflate(layoutInflater) }
    private val viewmodel by viewModels<CounrtyViewModel>()
    private val countryAdapter = CountryAdapter(this)
    private val allCountry = ArrayList<CountryData>()
    private lateinit var countrydao: CountryDao
    private val countryOffline = java.util.ArrayList<CountryData>()
    private var offline = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewmodel.getAllCountry()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        countrydao = CountryDatabase.getInstance().getcountryDao()


        lifecycleScope.launchWhenCreated {
            viewmodel.itemsCountry.collectLatest {
                when (it) {
                    UiEvent.Empty -> Unit
                    is UiEvent.Error -> {
                        requireActivity().displayToast("Error")
                        binding.progressBar.isVisible = false
                        offline = true
                        initOfflinePaging()
                    }
                    UiEvent.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is UiEvent.Success<*> -> {
                        binding.progressBar.isVisible = false
                        val countryList = it.data
                        allCountry.clear()
                        allCountry.addAll(countryList as Collection<CountryData>)
//                        countryAdapter.submitList(countryList as MutableList<CountryData>?)
                        offline = true
                        Log.d("CCC", countryList.toString())
                        countrydao.deleteAll()
                        for (item in countryList) {
                            item.let {
                                countrydao.inser(item)
                            }
                        }
                        initOfflinePaging()
                    }
                }
            }
        }



        initRv()

        searchItem()





        return binding.root
    }


    private fun initOfflinePaging() {
        binding.rvCountry.adapter = countryAdapter.withLoadStateFooter(MainLoadStateAdapter())
        lifecycleScope.launch {
            viewmodel.data.collectLatest { pagingData ->
                countryAdapter.submitData(pagingData)
            }
        }
    }


    private fun initRv() {
        binding.rvCountry.apply {
            adapter = countryAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }


    private fun searchItem() {
        binding.searchItem.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
//                var tempArr = ArrayList<CountryData>()
//                for (arr in allCountry) {
//                    if (arr.name!!.common!!.contains(query!!.trim(), true)) {
//                        tempArr.add(arr)
//                    }
//                }
////                countryAdapter.submitList(tempArr)
//                if (offline) {
//                    for (arr in countryOffline) {
//                        if (arr.name!!.common!!.contains(query!!.trim(), true)) {
//                            tempArr.add(arr)
//                        }
//                    }
//                }




                val trimmedQuery = query?.trim() ?: ""
                viewmodel.filterData(trimmedQuery).observe(viewLifecycleOwner) { pagingData ->
                    countryAdapter.submitData(viewLifecycleOwner.lifecycle, pagingData)
                }

                return true
            }
        })
    }

    fun getFilteredData(query: String): Flow<PagingData<CountryData>> {
        return Pager(
            config = PagingConfig(pageSize = PAGE_SIZE),
            pagingSourceFactory = { MyPagingSource(countrydao) }
        ).flow
    }

    override fun clickItem(countryData: CountryData) {
        Log.d("TTT", countryData.toString())
        bottomSheet(
            countryData.flags!!.png,
            countryData.region.toString(),
            countryData.subregion!!,
            countryData.name!!.official,
            countryData.name.nativeName.toString(),
            countryData.independent.toString(),
            countryData.startOfWeek.toString()
        )
    }

    private fun bottomSheet(
        img: String,
        capital: String,
        offical: String,
        region: String,
        subregion: String,
        are: String,
        description: String
    ) {
        val dialog = BottomSheetDialog(requireContext())
        val item = CustomBottomSheetBinding.inflate(layoutInflater)
        dialog.setContentView(item.root)
        //code

        Glide.with(requireContext()).load(img).into(item.imageCountry)


        item.tvCapital.text = "Region  ${capital}"
        item.tvOffial.text = "Sub Region  $capital"
        item.tvRegion.text = "Official $region"
        item.tvSubRegion.text = subregion
        item.tvArea.text = "Independent :  $are"
        item.tvAlt.text = "Start of Week $description"

        dialog.show()
    }


}
