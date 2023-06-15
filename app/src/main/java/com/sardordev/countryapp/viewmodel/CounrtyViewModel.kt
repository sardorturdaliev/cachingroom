package com.sardordev.countryapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.sardordev.countryapp.data.dt.db.CountryDatabase
import com.sardordev.countryapp.data.model.CountryData
import com.sardordev.countryapp.domain.repository.AppRepository
import com.sardordev.countryapp.utils.pagination.MyPagingSource
import com.sardordev.countryapp.utils.ResourceEvent
import com.sardordev.countryapp.utils.UiEvent
import com.sardordev.countryapp.utils.pagination.SearchPagination
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CounrtyViewModel @Inject constructor(
    private val appRepository: AppRepository,
    private val db: CountryDatabase
) : ViewModel() {
    private val _itemsCountry = MutableStateFlow<UiEvent>(UiEvent.Empty)
    val itemsCountry: StateFlow<UiEvent> get() = _itemsCountry
    private val _pagingData = MutableStateFlow<PagingData<CountryData>>(PagingData.empty())
    val pagingData: StateFlow<PagingData<CountryData>> = _pagingData

    fun getAllCountry() {
        viewModelScope.launch {
            _itemsCountry.value = UiEvent.Loading
            val result = appRepository.getAllCountry()
            when (result) {
                is ResourceEvent.Error -> {
                    _itemsCountry.value = UiEvent.Error(result.message!!)
                }
                is ResourceEvent.Success -> {
                    _itemsCountry.value = UiEvent.Success(result.data)
                }
            }
        }
    }

    private var currentQuery: String? = null



    fun fetchData() {
        viewModelScope.launch {
            val pager = Pager(config = pagingConfig) {
                MyPagingSource(db.getcountryDao())
            }
            val pagingDataFlow: Flow<PagingData<CountryData>> = pager.flow
            pagingDataFlow.collectLatest { pagingData ->
                _pagingData.value = pagingData
            }
        }
    }


    /*
    paging Config
     */
    private val pagingConfig = PagingConfig(
        pageSize = 20, // Number of items loaded at a time
        prefetchDistance = 5, // Number of items to prefetch
        enablePlaceholders = false // Show empty placeholders
    )

    val data = Pager(PagingConfig(pageSize = 6, enablePlaceholders = false, initialLoadSize = 6)) {
            MyPagingSource(db.getcountryDao())
        }.flow.cachedIn(viewModelScope)




    fun filterData(query: String): LiveData<PagingData<CountryData>> {
        val trimmedQuery = query.trim()
        if (currentQuery == trimmedQuery) {
            return data.asLiveData()
        }
        currentQuery = trimmedQuery
        val filteredData = Pager(PagingConfig(pageSize = 6, enablePlaceholders = false, initialLoadSize = 6)) {
            SearchPagination(db.getcountryDao(), trimmedQuery)
        }.flow.cachedIn(viewModelScope)
        return filteredData.asLiveData()
    }


}